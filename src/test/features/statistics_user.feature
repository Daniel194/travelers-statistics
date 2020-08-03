Feature: Receive user statistics

    Background:
        Given users with the following attributes
            | id  | login  | date       |
            | 100 | other1 | 2020-01-01 |
            | 200 | other2 | 2020-01-01 |
            | 300 | other3 | 2020-01-02 |
            | 400 | other4 | 2020-01-03 |

        When user already exists

    Scenario: Get user statistics
        When admin wants to see user statistics between '2020-01-01' and '2020-01-03'
        Then the response is SUCCESSFUL
        And following user statistics are returned
            | date       | count |
            | 2020-01-01 | 2     |
            | 2020-01-02 | 1     |
            | 2020-01-03 | 1     |
