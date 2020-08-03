Feature: Create new user
    Create new user based on information received

    Background:
        Given user with the following attributes
            | id  | login | date       |
            | 400 | other | 2020-01-01 |

        When user already exists

    Scenario Outline: Create user entry <testCase> <expectedResult>
        Given user wants to create an account with the following attributes
            | login   |
            | <login> |
        When user save the new account '<testCase>'
        Then the user save is '<expectedResult>'
        Examples:
            | testCase            | expectedResult | login |
            | WITH UNIQUE LOGIN   | SUCCESSFUL     | test1 |
            | WITHOUT LOGIN       | FAIL           |       |
            | WITH EXISTING LOGIN | SUCCESSFUL     | other |
