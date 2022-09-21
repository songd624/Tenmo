# Tenmo
Congratulations—you've landed a job with TEnmo, whose product is an online payment service for transferring "TE bucks" between friends. However, they don't have a product yet. You've been tasked with finalizing the server side of the application: a database and a RESTful API server.

You will need to add controllers, models, DAOs, and database tables to implement the following features:

Use cases
Required use cases
You should attempt to complete all of the following required use cases.

[COMPLETE] As a user of the system, I need to be able to register myself with a username and password.
The ability to register has been provided in your starter code.
[COMPLETE] As a user of the system, I need to be able to log in using my registered username and password.
Logging in returns an Authentication Token. I need to include this token with all my subsequent interactions with the system outside of registering and logging in.
The ability to log in has been provided in your starter code.
User ids start at 1001.
As a user, when I register a new account is created for me.
The new account has an initial balance of $1000.
Account ids start at 2001.
As an authenticated user of the system, I need to be able to see my Account Balance.
As an authenticated user of the system, I need to be able to send a transfer of a specific amount of TE Bucks to a registered user.
I should be able to choose from a list of users to send TE Bucks to.
I must not be allowed to send money to myself.
A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
The receiver's account balance is increased by the amount of the transfer.
The sender's account balance is decreased by the amount of the transfer.
I can't send more TE Bucks than I have in my account.
I can't send a zero or negative amount.
A Sending Transfer has an initial status of Approved.
Transfer ids start at 3001.
As an authenticated user of the system, I need to be able to see transfers I have sent or received.
As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.
Validate all of the API's endpoints using Postman and/or Swagger. No sensitive information (i.e. account numbers, user ids, etc) should be passed in the URL. Integration testing is required on each method that connects to the database -- remember WET! Write everything at least twice!

Challenge use cases
As an authenticated user of the system, I need to be able to request a transfer of a specific amount of TE Bucks from another registered user.
I should be able to choose from a list of users to request TE Bucks from.
I must not be allowed to request money from myself.
I can't request a zero or negative amount.
A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
A Request Transfer has an initial status of Pending.
No account balance changes until the request is approved.
The transfer request should appear in both users' list of transfers (use case #5).
As an authenticated user of the system, I need to be able to see my Pending transfers.
As an authenticated user of the system, I need to be able to either approve or reject a Request Transfer.
I can't "approve" a given Request Transfer for more TE Bucks than I have in my account.
The Request Transfer status is Approved if I approve, or Rejected if I reject the request.
If the transfer is approved, the requester's account balance is increased by the amount of the request.
If the transfer is approved, the requestee's account balance is decreased by the amount of the request.
If the transfer is rejected, no account balance changes.
Bonus Challenge
If you complete all of the required and challenge use cases and are looking for yet another challenge, create a Command Line Interface (CLI) client application for TEnmo. The file CLI.md contains sample user experiences for inspiration.

How to set up the database
Create a new Postgres database called tenmo. Run the database/tenmo.sql script in pgAdmin to set up the database that you'll begin to work from. You should make structure changes in this script and not the database directly. Additionally, both you and your team members need to run this script each time after making changes to it.

Database schema
The following tables are created by the provided tenmo.sql script.

tenmo_user table
Stores the login information for users of the system.

Field	Description
user_id	Unique identifier of the user
username	String that identifies the name of the user; used as part of the login process
password_hash	Hashed version of the user's password
account table
Stores the accounts of users in the system.

Field	Description
account_id	Unique identifier of the account
user_id	Foreign key to the tenmo_user table; identifies user who owns account
balance	The amount of TE bucks currently in the account
