Insurance Customer/User Management System
Week 1 Task: Account management 
create Customer/User Management for insurance project.
•	User Registration
•	User Profile Management
•	Update User Details
•	View User Details
•	Delete User
Introduction: 
The Insurance Customer/User Management System is a backend application developed to manage customer accounts securely. The application provides user registration, login authentication, profile management, address management, password management, and KYC document upload functionality.
The system follows a layered architecture consisting of Controller, Service, Repository, and Database layers.
The application uses JWT (JSON Web Token) based authentication to secure APIs and BCrypt encryption to protect user passwords.
Objectives:
The main objective of this project is to provide a secure platform where users can:
•	Register an account
•	Login and Logout
•	Manage profile information
•	Change password securely
•	Manage address information
•	Maintain login history
•	Access APIs using JWT authentication
Technology Stack:
Backend
•	Java 17
•	Spring Boot
•	Spring Data JPA
•	Hibernate
Database
•	MySQL
Security
•	Spring Security
•	JWT Token
•	BCrypt Password Encryption
Testing
•	Postman

User Roles:
The system supports three user roles.
USER - A customer who can access and manage their personal information.
AGENT - An insurance agent who can interact with customer-related activities.
SUPER_AGENT - An administrative role with higher-level access and management capabilities.
System Architecture:
Client / Postman
|
Controller Layer
|
Service Layer
|
Repository Layer
|
MySQL
Modules Implemented:
1.	User Registration
Features: 
•	Unique email validation
•	Password encryption using BCrypt
•	User type assignment
•	User information storage
Registration Process:
User enters registration details
↓
Input validation
↓
Password encryption
↓
User saved into database
↓
Registration successful
2.	Login Module
Features
•	Email verification
•	Password verification
•	JWT token generation
•	Login tracking
Login Process:
User enters email and password
↓
Credentials validated
↓
JWT Token generated
↓
Login record saved
↓
User successfully logged in
3.	JWT Authentication:
Purpose: Instead of entering username and password for every request, users access APIs using a generated token.
Flow:
Login
↓
JWT Token Generated
↓
Token Sent to Client
↓
Authorization Header
↓
Protected APIs Accessed
4.	User Profile Management:
Features
•	View user details
•	View all users
•	Update profile information
•	Delete user account
5.	Address Management:
Features
•	Add Address
•	View Addresses
•	Update Address
•	Delete Address
6.	Change Password Module:
Features
•	Old password verification
•	New password confirmation
•	Encrypted password storage
7.	Logout Module:
Features
•	Updates login status
•	Records logout information
•	Prevents further use of logged-out token

Database Design:
Users Table
users
------------------------------------------------
id (PK)
user_id
first_name
last_name
email
password
phone_number
date_of_birth
gender
status
create_date
update_date
user_type_id (FK)

Address Table
address
------------------------------------------------
address_id (PK)
city
state
pin_code
user_id (FK)

User Login Table
user_login
------------------------------------------------
login_id (PK)
token
login_time
token_start_time
token_end_time
status
user_id (FK)

User Type Table:
user_type
------------------------------------------------
user_type_id (PK)
user_type_name

Project Structure:

insurance-user-management
│
├── controller
│ ├── AuthController
│ ├── UserController
│ └── AddressController
│
├── service
│ ├── UserService
│ ├── AddressService
│ ├── LoginService
│ └── Impl
│
├── repository
│ ├── UserRepository
│ ├── AddressRepository
│ └── UserLoginRepository
│
├── entity
│ ├── User
│ ├── Address
│ └── UserLogin
│
├── dto
│ ├── UserRequestDto
│ ├── UserResponseDto
│ ├── LoginRequestDto
│ └── LoginResponseDto
│
├── exception
│ ├── ResourceNotFoundException
│ ├── UserAlreadyExistsException
│ └── GlobalExceptionHandler
│
├── security
│ ├── JwtUtil
│ ├── JwtFilter
│ └── SecurityConfig
│
└── InsuranceUserManagementApplication


Project Workflow:
Register User
↓
Login
↓
JWT Token Generated
↓
View Profile
↓
Update Profile
↓
Manage Addresses
↓
Change Password
↓
Logout
Week 2 Task:  Policy Management Module
Develop a Policy Management Module for the Insurance System that enables customers to view insurance plans, compare plans, purchase policies, track policy status, renew policies, and download policy information.
The module should maintain insurance plans categorized by policy types and establish proper relationships between customers, insurance plans, and purchased policies.
Functional Requirements:
•	Policy Type Management
•	Insurance Plan Management
•	View Available Insurance Plans
•	Compare Insurance Plans
•	Purchase Insurance Policy
•	View Purchased Policies
•	View Policy Status
•	Renew Policy
•	Download Policy Information
Modules Implemented:

1.	Policy Type Management
Features:
•	Create Policy Type
•	View Policy Type
•	Categorize Insurance Plans
•	Policy Types:
	HEALTH_INSURANCE
	LIFE_INSURANCE
	CAR_INSURANCE
	TRAVEL_INSURANCE
	TERM_INSURANCE
2.	Insurance Plan Management
Features:
•	Create Insurance Plans
•	View Insurance Plans
•	Manage Coverage Information
•	Maintain Premium Information
•	Sample Plans:
	Health Basic
	Health Premium
	Life Secure
	Life Plus
	Car Basic
	Car Premium
	Travel Shield
	Travel International
	Term Basic
	Term Premium

3.	View Available Insurance Plans
 Features:
•	View Active Plans
•	View Coverage Amount
•	View Premium Amount
•	View Policy Term
4.	Compare Insurance Plans
Features:
•	Compare Multiple Plans
•	Compare Coverage Amount
•	Compare Premium Amount
•	Compare Policy Term
5.	Purchase Policy
Features:
•	Purchase Insurance Plan
•	Generate Policy Number
•	Store Customer Policy Information
6.	View Purchased Policies
Features:
•	View Purchased Plans
•	View Policy Details
•	View Policy History
7.	Policy Status Management
Status Values:
•	ACTIVE
•	RENEWED
•	EXPIRED
•	CANCELLED

8.	Policy Renewal
Features:
•	Extend Policy Validity
•	Update Policy Status
•	Maintain Renewal Information
9.	Download Policy
Features:
•	Download Policy Information
•	View Policy Data

Database Tables:

Policy Type Table
policy_type
----------------------------------
policy_type_id
policy_type_name

Insurance Plan Table
insurance_plan
--------------------------------------
insurance_plan_id
plan_name
coverage_amount
premium_amount
policy_term
description
status
policy_type_id

Customer Policy Table
customer_policy
-----------------------------------------------
customer_policy_id
policy_number
purchase_date
start_date
end_date
premium_amount
status
user_id
insurance_plan_id

Project Structure:
controller
│
├── PolicyTypeController
├── InsurancePlanController
└── CustomerPolicyController

service
│
├── PolicyTypeService
├── InsurancePlanService
├── CustomerPolicyService

service.impl
│
├── PolicyTypeServiceImpl
├── InsurancePlanServiceImpl
└── CustomerPolicyServiceImpl

repository
│
├── PolicyTypeRepository
├── InsurancePlanRepository
└── CustomerPolicyRepository

entity
│
├── PolicyType
├── InsurancePlan
└── CustomerPolicy

dto
│
└── PurchasePolicyRequest

exception
│
└── ResourceNotFoundException

Project Flow:

Login
↓
View Available Plans
↓
Compare Plans
↓
Purchase Policy
↓
View Purchased Policies
↓
View Policy Status
↓
Renew Policy
↓
Download Policy

Week 3 Task: Payment & Claim Management Module
Introduction:
The Payment Management and Claim Management Module is developed as part of the Insurance Management System. This module enables customers to securely make premium payments, manage payment history, download payment receipts, submit insurance claims, upload claim documents, track claim status.
Objectives:
The main objective of this module is to allow customers to:
•	Make insurance premium payments
•	Track payment history
•	Download payment receipts
•	Submit insurance claims
•	Upload supporting claim documents
•	Track claim status
•	View claim history
Technology Stack:
Backend
•	Java 17
•	Spring Boot
•	Spring Data JPA
•	Hibernate
Database
•	MySQL
Payment Gateway
•	Razorpay
PDF Generation
•	iText PDF
Testing
•	Postman
Functional Requirements:
Payment Management Module
Features
•	Create Payment
•	Razorpay Integration
•	Payment Success Processing
•	Payment History
•	Payment Receipt Download
•	Transaction Tracking
Claim Management Module
Features
•	Submit Claim
•	View Claim History
•	Track Claim Status
•	View Claim Details
•	Upload Claim Documents
•	View Uploaded Documents
Modules Implemented:
1. Payment Management Module
Features
•	Premium Payment Processing
•	Razorpay Order Tracking
•	Payment Status Tracking
•	Payment History
•	Receipt Generation
Payment Process Flow:

Purchase Policy
↓
Create Payment
↓
Generate Transaction ID
↓
Redirect To Razorpay
↓
Payment Success
↓
Update Payment Status
↓
Generate Receipt
Payment Status: PENDING, SUCCESS, FAILED
Payment Information Stored:
Payment ID
Transaction ID
Payment Amount
Payment Method
Payment Date
Payment Status
Razorpay Order ID
Razorpay Payment ID
Customer Policy
2. Payment Receipt Generation
Features
•	Generate Receipt PDF
•	Download Receipt
•	View Transaction Details
PDF Contains
Payment ID
Payment Amount
Payment Method
Payment Status
Transaction ID
Razorpay Order ID
Razorpay Payment ID
Library Used
iText PDF
3. Claim Management Module
Features
•	Submit Claim
•	View Claims
•	View Claim History
•	View Claim Details
•	Track Claim Status
Claim Submission Process

Customer Purchases Policy
↓
Submit Claim
↓
Claim Validation
↓
Claim Saved
↓
Status = SUBMITTED
↓
Notification Generated
Claim Information Stored
Claim ID
Claim Amount
Claim Reason
Claim Date
Claim Status
User Information
Policy Information
Claim Status Management
Status Values: SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, SETTLED
Claim Workflow: 
SUBMITTED
↓
UNDER_REVIEW
↓
APPROVED
↓
SETTLED
(Or)
SUBMITTED
↓
UNDER_REVIEW
↓
REJECTED
4. Claim Document Management
Features
•	Upload Claim Documents
•	View Uploaded Documents
•	Associate Document with Claim
Supported Documents
1.	Medical Bills
2.	Hospital Reports
3.	Vehicle Repair Bills
4.	Travel Documents
5.	Identity Proof
6.	Supporting Documents
Claim Document Upload Flow

Create Claim
↓
Upload Document
↓
Validate Claim
↓
Store Document
↓
View Documents
Database Design:
Payment Table
payment
------------------------------------------------
payment_id (PK)
transaction_id
payment_amount
payment_method
payment_date
payment_status
razorpay_order_id
razorpay_payment_id
customer_policy_id (FK)

Claim Table
claim
------------------------------------------------
claim_id (PK)
claim_amount
claim_reason
rejection_reason
settlement_amount
claim_date
status
user_id (FK)
customer_policy_id (FK)

Claim Document Table
claim_document
------------------------------------------------
claim_document_id (PK)
document_name
document_path
claim_id (FK)

Project Structure:
controller│├── PaymentController
    ├── ClaimController
         └── ClaimDocumentController

service│├── PaymentService
  ├── ClaimService
    └── ClaimDocumentService

service. impl│├── PaymentServiceImpl
 ├── ClaimServiceImpl
 └── ClaimDocumentServiceImpl

repository│├── PaymentRepository
         ├── ClaimRepository
         └── ClaimDocumentRepository

entity│├── Payment
 ├── PaymentStatus
 ├── Claim
 ├── ClaimStatus
  └── ClaimDocument

dto│├── PaymentRequest
         ├── CreateClaimRequest
         └── ClaimDocumentRequest

exception │└── ResourceNotFoundException

Payment Management Workflow:

Customer Login
↓
View Purchased Policies
↓
Select Policy
↓
Pay Premium - Razo Pay
↓
Transaction Generated
↓
Payment Successful
↓
Payment History Updated
↓
Download Receipt


Claim Management Workflow:

Customer Login
↓
View Purchased Policies
↓
Select Policy
↓
Submit Claim
↓
Upload Claim Documents
↓
Claim Created
↓
Status = SUBMITTED
↓
Track Claim Status
↓
View Claim History

Week 4 Task: Notification Management Module

Introduction:
The Notification Management Module is integrated with the Insurance Management System to provide real-time alerts and communication to customers. The module automatically generates notifications for important business events such as user registration, policy purchase, premium payment, claim submission, claim approval/rejection, and policy renewal.
The notifications are stored in the database and displayed through the Notification Center. Each notification maintains its own status as READ or UNREAD.

Objectives:
The main objectives are:
•	Notify users about important activities
•	Provide payment confirmation alerts
•	Provide claim status updates
•	Provide policy renewal notifications
•	Maintain notification history
•	Support notification status tracking
•	Improve customer experience and communication

Features Implemented:
1. Registration Notification
Features
•	Welcome notification
•	Account creation confirmation
•	Registration success alert
•	Example: 
Title: Registration Successful
Message:	
     Welcome to My Insurance.
    Your account has been created successfully.
2. Policy Purchase Notification
Features
•	Policy purchase confirmation
•	Purchased plan information
•	Customer acknowledgement
•	Example: 
Title:  Policy Purchased
Message: You successfully purchased Travel Shield Policy.
3. Premium Payment Notification
Features
•	Payment success notification
•	Premium payment acknowledgement
•	Transaction confirmation
•	Example: 
Title: Payment Successful
Message: Premium payment completed successfully.
4. Claim Notification
Features
•	Claim submission notification
•	Claim approval notification
•	Claim rejection notification
•	Claim settlement notification

5. Policy Renewal Notification
Features
•	Renewal confirmation
•	Policy status update

6. Notification History
Features
•	View all notifications
•	Sort notifications by date
•	Maintain notification history
•	Track notification status

7. Notification Status Management
Status Values: READ, UNREAD

Workflow: 
Notification Created
↓
Status = UNREAD
↓
User Opens Notifications
↓
Mark All Read
↓
Status = READ

Database Design:
Notification Table
notification
------------------------------------------------
notification_id (PK)
title
message
notification_type
created_date
status
user_id (FK)

Project Structure:

controller│
└── NotificationController

service│
└── NotificationService

service.impl│
└── NotificationServiceImpl

repository│
└── NotificationRepository

entity│
└── Notification

dto│
└── NotificationRequest

Notification Workflow:

Registration:

User Registration
↓
Account Created
↓
Registration Notification
Policy Purchase:
Purchase Policy
↓
Policy Created
↓
Policy Purchase Notification


Premium Payment:

Payment Successful
↓
Notification Generated
↓
User Notification Updated

Claim Management:

Submit Claim
↓
Claim Submitted Notification

Admin Reviews Claim
↓
Claim Approved / Rejected
↓
Notification Generated

Claim Settled
↓
Settlement Notification

Policy Renewal:
Renew Policy
↓
Policy Renewed
↓
Renewal Notification

Final Flow Of this task:

User Registration
↓
Login (JWT Authentication)
↓
Profile Management
↓
Address Management
↓
KYC Upload
↓
View Insurance Plans
↓
Compare Plans
↓
Purchase Policy
↓
Policy Created
↓
Razorpay Payment
↓
Payment Success
↓
Payment Receipt
↓
Notifications Generated
↓
View My Policies
↓
Renew Policy
↓
Submit Claim
↓
Upload Claim Documents
↓
Track Claim Status
↓
Receive Notifications
↓
Logout

