Feature: Create new country
    Create new country based on information received

    Background:
        Given country with the following attributes
            | id  | login | country | date       |
            | 400 | other | ROM     | 2020-01-01 |

        When country already exists

    Scenario Outline: Create country entry <testCase> <expectedResult>
        Given user wants to add a country with the following attributes
            | login   | country   |
            | <login> | <country> |
        When user save the new country '<testCase>'
        Then the country save is '<expectedResult>'
        Examples:
            | testCase        | expectedResult | login | country |
            | WITH ALL FIELDS | SUCCESSFUL     | other | USA     |
            | WITHOUT LOGIN   | FAIL           |       | USA     |
            | WITHOUT COUNTRY | FAIL           | other |         |
            | WITHOUT FIELDS  | FAIL           |       |         |
