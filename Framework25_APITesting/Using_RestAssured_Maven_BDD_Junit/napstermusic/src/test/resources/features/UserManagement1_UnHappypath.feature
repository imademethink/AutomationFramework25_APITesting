Feature: Demo User management v2 rest api un-happy path

  @registration_invalid
  Scenario: User registration
    When Register user with invalid data
    Then Registration should NOT be successful

  @login_invalid
  Scenario: Login with invalid data
    When Registration with invalid user data should be successful
    Then Login with invalid user data should NOT be successful

  @update_profile_invalid
  Scenario: Update profile with invalid data
    When Registration with invalid user data should be successful
    Then Login with valid user data should be successful
    Then Update profile with invalid data should NOT be successful
