package com.insurance.controller;

import com.insurance.dto.*;
import com.insurance.entity.*;
import com.insurance.repository.*;
import com.insurance.security.JwtUtil;
import com.insurance.service.*;
import com.razorpay.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KycDocumentService kycDocumentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PolicyTypeService policyTypeService;

    @Autowired
    private InsurancePlanService insurancePlanService;

    @Autowired
    private CustomerPolicyService customerPolicyService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ClaimService claimService;

    @Autowired
    private ClaimDocumentService claimDocumentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/login-page")
    public String login() {
        return "login";
    }

    @GetMapping("/account-center")
    public String accountCenter() {

        return "account-center";
    }

    @GetMapping("/register-page")
    public String register() {
        return "register";
    }

//    @PostMapping("/register-page")
//    public String saveRegister(@RequestParam String userType) {
//
//        if ("USER".equalsIgnoreCase(userType)) {
//            System.out.println("User Registered Successfully");
//        } else if ("AGENT".equalsIgnoreCase(userType)) {
//            System.out.println("Agent Registered Successfully");
//        } else if ("SUPER_AGENT".equalsIgnoreCase(userType)) {
//            System.out.println("Super Agent Registered Successfully");
//        }
//        return "login";
//    }


    @PostMapping("/register-page")
    public String saveRegister(

            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phoneNumber,
            @RequestParam String dateOfBirth,
            @RequestParam String gender,
            @RequestParam String status,
            @RequestParam Long userTypeId

    ) {

        UserRequest request = new UserRequest();

        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setPassword(password);
        request.setPhoneNumber(phoneNumber);
        request.setDateOfBirth(
                java.time.LocalDate.parse(dateOfBirth));
        request.setGender(gender);
        request.setStatus(status);
        request.setUserTypeId(userTypeId);

        userService.registerUser(request);

        //notification
        User user = userRepository.findByEmail(email).orElseThrow();

        NotificationRequest notification = new NotificationRequest();

        notification.setUserId(user.getUserId());

        notification.setTitle("Registration Successful");

        notification.setMessage("Welcome to My Insurance. Your account has been created successfully.");

        notification.setNotificationType("REGISTRATION");

        notificationService.createNotification(notification);

        System.out.println(
                "Registered User : " +
                        firstName + " " + lastName);



        return "redirect:/login-page";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if (token == null) {
            return "redirect:/login-page";
        }

        model.addAttribute("userName", session.getAttribute("userName"));

        return "dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {

//        String token = (String) session.getAttribute("token");
//
//        if (token == null) {
//            return "redirect:/login-page";
//        }
        String token =
                (String) session.getAttribute("token");

        System.out.println("TOKEN = " + token);

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "redirect:/login-page";
        }

        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User formUser, HttpSession session, RedirectAttributes redirectAttributes) {

        String token = (String) session.getAttribute("token");
        System.out.println("TOKEN = " + token);

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        user.setFirstName(formUser.getFirstName());
        user.setLastName(formUser.getLastName());
        user.setPhoneNumber(formUser.getPhoneNumber());
        user.setDateOfBirth(formUser.getDateOfBirth());
        user.setGender(formUser.getGender());

        userRepository.save(user);

        session.setAttribute("userName", user.getFirstName());

        redirectAttributes.addFlashAttribute("successMessage", "Profile Updated Successfully");

        return "redirect:/dashboard";
        // return "redirect:/profile?success=true";
    }

    @GetMapping("/address")
    public String address(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        System.out.println("TOKEN = " + token);

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        model.addAttribute("addresses", user.getAddresses());

        model.addAttribute("address", new Address());

        return "address";
    }

    @PostMapping("/address/save")
    public String saveAddress(Address address, HttpSession session) {

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        address.setUser(user);

        addressRepository.save(address);

        return "redirect:/address";
    }

    @GetMapping("/address/edit/{id}")
    public String editAddress(@PathVariable Long id, Model model, HttpSession session) {

        Address address = addressRepository.findById(id).orElseThrow();

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        model.addAttribute("addresses", user.getAddresses());

        model.addAttribute("address", address);

        return "address";
    }

    @GetMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {

        addressRepository.deleteById(id);

        return "redirect:/address";
    }

    @GetMapping("/change-password")
    public String changePassword() {

        return "change-password";
    }

    @GetMapping("/kyc")
    public String kyc(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        model.addAttribute("documents", kycDocumentService.getUserKyc(user.getUserId()));

        return "kyc";
    }

    @PostMapping("/kyc/upload")
    public String uploadKyc(@RequestParam("file") MultipartFile file, @RequestParam("documentType") String documentType, HttpSession session) {

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        kycDocumentService.uploadDocument(user.getId(), file, documentType);

        return "redirect:/kyc";
    }

   // @GetMapping("/policies")
   // public String policies(@RequestParam(required = false) Long policyTypeId, Model model){
   @GetMapping("/policies")
   public String policies(

           @RequestParam(required = false)
           Long policyTypeId,

           @RequestParam(required = false)
           String planName,

           @RequestParam(required = false)
           Double premiumAmount,

           @RequestParam(required = false)
           Integer policyTerm,

           Model model) {

        model.addAttribute("policyTypes", policyTypeService.getAllPolicyTypes());

        List<InsurancePlan> plans;

        plans = insurancePlanService.getAllPlans();

        if(policyTypeId != null){
            plans = plans.stream()
                    .filter(plan ->
                            plan.getPolicyType()
                                    .getPolicyTypeId()
                                    .equals(policyTypeId)).toList();
        }
        if(planName != null && !planName.isBlank()){
            plans = plans.stream().filter(plan ->
                            plan.getPlanName()
                                    .toLowerCase()
                                    .contains(planName.toLowerCase())).toList();
        }
        if(premiumAmount != null){
            plans = plans.stream()
                    .filter(plan ->
                            plan.getPremiumAmount() <= premiumAmount).toList();
        }
       if(policyTerm != null){
           plans = plans.stream().filter(plan ->
                           plan.getPolicyTerm().equals(policyTerm)).toList();
       }

        model.addAttribute("plans", plans);
        if(plans.isEmpty()){

            model.addAttribute(
                    "noPoliciesMessage",
                    "No policies found for the selected criteria.");
        }

        return "policies";
    }



    //
//    @GetMapping("/policies/buy/{planId}")
//    public String buyPolicy(@PathVariable Long planId, HttpSession session) {
//
//        String token = (String) session.getAttribute("token");
  //  if(token == null || token.isBlank()) {
  //      return "redirect:/login-page";
  //  }
//
//        String email = jwtUtil.extractUsername(token);
//
//        User user = userRepository.findByEmail(email).orElseThrow();
//
//        PurchasePolicyRequest request = new PurchasePolicyRequest();
//
//        request.setUserId(user.getUserId());
//
//        request.setInsurancePlanId(planId);
//
//        customerPolicyService.purchasePolicy(request);
//
//        return "redirect:/my-policies";
//    }
    @GetMapping("/my-policies")
    public String myPolicies(HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        model.addAttribute("policies", customerPolicyService.getPoliciesByUser(user.getUserId()));

        return "my-policies";
    }

    @GetMapping("/download/{customerPolicyId}")
    public ResponseEntity<byte[]> downloadPolicy(@PathVariable Long customerPolicyId) throws Exception {

        System.out.println("DOWNLOAD CALLED : " + customerPolicyId);

        return customerPolicyService.downloadPolicy(customerPolicyId);
    }

    @GetMapping("/renew-policy/{id}")
    public String renewPolicy(@PathVariable Long id, Model model){
        CustomerPolicy policy = customerPolicyService.getPolicyById(id);
        model.addAttribute("policy",policy);
        return "renew-policy";
    }

    @GetMapping("/renew-policy/confirm/{id}")
    public String confirmRenewPolicy(@PathVariable Long id) {

        CustomerPolicy policy = customerPolicyService.renewPolicy(id);

        NotificationRequest notification = new NotificationRequest();

        notification.setUserId(policy.getUser().getUserId());

        notification.setTitle("Policy Renewed");

        notification.setMessage("Your policy has been renewed successfully.");

        notification.setNotificationType("RENEWAL");

        notificationService.createNotification(notification);

        return "redirect:/my-policies";
    }

//    @GetMapping("/policies/buy/{planId}")
//    public String buyPolicy(@PathVariable Long planId, HttpSession session, Model model) throws Exception {
//
//        String token = (String) session.getAttribute("token");
//
//        String email = jwtUtil.extractUsername(token);
//
//        User user = userRepository.findByEmail(email).orElseThrow();
//
//        PurchasePolicyRequest request = new PurchasePolicyRequest();
//
//        request.setUserId(user.getUserId());
//
//        request.setInsurancePlanId(planId);
//
//        CustomerPolicy policy = customerPolicyService.purchasePolicy(request);
//
//        model.addAttribute("policy", policy);
//
//        // return "payments";
//        Order order =
//                razorpayService.createOrder(policy.getPremiumAmount());
//
//        model.addAttribute("policy", policy);
//
//        model.addAttribute(
//                "orderId",
//                order.get("id"));
//
//        model.addAttribute(
//                "keyId",
//                "rzp_test_TRx3d2Rpl5Nc5r");
//
//        return "payments";
//    }

    @GetMapping("/payments")
    public String payments(Model model) {

        model.addAttribute(
                "payments",
                paymentRepository.findAll());

        return "payment-history";
    }

    @GetMapping("/policies/buy/{planId}")
    public String buyPolicy(@PathVariable Long planId,
                            HttpSession session,
                            Model model) throws Exception {

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        PurchasePolicyRequest request = new PurchasePolicyRequest();

        request.setUserId(user.getUserId());
        request.setInsurancePlanId(planId);

        CustomerPolicy policy = customerPolicyService.purchasePolicy(request);

        Order order = razorpayService.createOrder(policy.getPremiumAmount());

        System.out.println("ORDER = " + order);
        System.out.println("ORDER ID = " + order.get("id"));

        System.out.println("KEY = " + "rzp_test_TUK9Jq2Ytp45E7");
        System.out.println("ORDER ID = " + order.get("id"));

        model.addAttribute("policy", policy);
        model.addAttribute("orderId", order.get("id"));
       // model.addAttribute("keyId", "rzp_test_TUJGzyeSViPxqR");

        model.addAttribute("keyId", razorpayKeyId);

        System.out.println("RAZORPAY KEY = " + razorpayKeyId);
        return "payments";
    }
//    @GetMapping("/payment/success")
//    public String paymentSuccess(
//            @RequestParam Long customerPolicyId,
//            @RequestParam Double paymentAmount,
//            @RequestParam String paymentMethod,
//            @RequestParam String razorpayPaymentId) {
//
//        PaymentRequest request = new PaymentRequest();
//
//        request.setCustomerPolicyId(customerPolicyId);
//        request.setPaymentAmount(paymentAmount);
//        request.setPaymentMethod(paymentMethod);
//
//        Payment payment =
//                paymentService.createPayment(request);
//
//        System.out.println(
//                "Razorpay Payment Id = "
//                        + razorpayPaymentId);
//
//        paymentService.completePayment(
//                payment.getPaymentId(),
//                razorpayPaymentId);
//
//        return "redirect:/my-policies";
//    }
    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam Long customerPolicyId,
            @RequestParam Double paymentAmount,
            @RequestParam String paymentMethod,
            @RequestParam String razorpayPaymentId,
            @RequestParam String razorpayOrderId) {

        PaymentRequest request = new PaymentRequest();

        request.setCustomerPolicyId(customerPolicyId);
        request.setPaymentAmount(paymentAmount);
        request.setPaymentMethod(paymentMethod);

        request.setRazorpayPaymentId(razorpayPaymentId);
        request.setRazorpayOrderId(razorpayOrderId);

        Payment payment = paymentService.createPayment(request);

        paymentService.completePayment(payment.getPaymentId(), razorpayPaymentId);

        CustomerPolicy policy = customerPolicyService.getPolicyById(customerPolicyId);

        //notication
        NotificationRequest notification = new NotificationRequest();

        notification.setUserId(policy.getUser().getUserId());

        notification.setTitle("Payment Successful");

        notification.setMessage("Premium payment completed successfully.");

        notification.setNotificationType("PAYMENT");

        notificationService.createNotification(notification);

        return "redirect:/my-policies";
    }
    @GetMapping("/payment/failed")
    public String paymentFailed(@RequestParam Long paymentId){

        paymentService.failedPayment(paymentId);
        return "redirect:/payments";
    }

    @GetMapping("/payment-history")
    public String paymentHistory(Model model) {

        model.addAttribute("payments", paymentRepository.findAll());

        return "payment-history";
    }
    @GetMapping("/claims")
    public String claimsPage(HttpSession session, Model model){

        String token = (String) session.getAttribute("token");

        if(token == null){
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        model.addAttribute("claims", claimService.getClaimHistoryByUser(user.getUserId()));

        return "claims";
    }
    @GetMapping("/claim-status/{id}")
    public String claimStatus(@PathVariable Long id, Model model){

        model.addAttribute("status", claimService.getClaimStatus(id));

        return "claim-status";
    }
    @GetMapping("/claim-documents/{id}")
    public String claimDocuments(@PathVariable Long id, Model model){

        model.addAttribute("claimId", id);

        model.addAttribute("documents", claimDocumentService.getDocumentsByClaim(id));

        return "claim-documents";
    }


    @PostMapping("/claims/submit")
    public String submitClaim(
            @RequestParam Long customerPolicyId,
            @RequestParam Double claimAmount,
            @RequestParam String claimReason,
            HttpSession session) {

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        CreateClaimRequest request = new CreateClaimRequest();

        request.setUserId(user.getUserId());

        request.setCustomerPolicyId(customerPolicyId);

        request.setClaimAmount(claimAmount);

        request.setClaimReason(claimReason);

        claimService.submitClaim(request);

        return "redirect:/claims";
    }
    @PostMapping("/claim-documents/upload")
    public String uploadClaimDocument(
            @RequestParam Long claimId,
            @RequestParam String documentName,
            @RequestParam("file") MultipartFile file)
            throws Exception {

        String uploadDir = "C:/claim-documents/";

        File dir = new File(uploadDir);

        if(!dir.exists()){
            dir.mkdirs();
        }

        String filePath = uploadDir + file.getOriginalFilename();

        file.transferTo(new File(filePath));
        ClaimDocumentRequest request = new ClaimDocumentRequest();

        request.setClaimId(claimId);

        request.setDocumentName(documentName);

        request.setDocumentPath(filePath);

        claimDocumentService.uploadDocument(request);

        return "redirect:/claim-documents/" + claimId;
    }


    @GetMapping("/notifications")
    public String notifications(HttpSession session, Model model){

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        model.addAttribute("notifications", notificationService.getUserNotifications(user.getUserId()));

        return "notifications";
    }

    @GetMapping("/notifications/read-all")
    public String readAllNotifications(HttpSession session){

        String token = (String) session.getAttribute("token");

        if(token == null || token.isBlank()) {
            return "redirect:/login-page";
        }

        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        notificationService.markAllAsRead(user.getUserId());

        return "redirect:/notifications";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {

        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if(!newPassword.equals(confirmPassword)) {

            redirectAttributes.addFlashAttribute("error", "Passwords do not match");

            return "redirect:/forgot-password";
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null){

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Email not found");
            return "redirect:/forgot-password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Password reset successfully");

        return "redirect:/login-page";
    }
    //Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login-page";
    }
}