Feature: Demo User management v2 rest api happy path

  @registration
  Scenario: User registration
    When Register user with valid data
    Then Registration should be successful

  @ene2end
  Scenario: Login un-happy path case
    Then Registration with valid user data should be successful
    Then Login with valid user data should be successful
    When Update profile with valid data should be successful
    Then Logout with valid user data should be successful
