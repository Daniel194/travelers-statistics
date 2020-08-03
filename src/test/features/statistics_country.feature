Feature: Receive country statistics

    Background:
        Given countries with the following attributes
            | id  | login  | country | date       |
            | 100 | other1 | ROM     | 2020-01-01 |
            | 200 | other2 | USA     | 2020-01-01 |
            | 300 | other3 | ROM     | 2020-01-01 |
            | 400 | other4 | BRA     | 2020-01-02 |
            | 500 | other5 | BRA     | 2020-01-02 |
            | 600 | other6 | USA     | 2020-01-03 |
            | 700 | other7 | ROM     | 2020-01-04 |

        When countries already exists

    Scenario: Get country statistics by date
        When admin wants to see country statistics between '2020-01-01' and '2020-01-04'
        Then the response is SUCCESSFUL
        And following country statistics are returned
            | country | count |
            | ROM     | 3     |
            | USA     | 2     |
            | BRA     | 2     |

    Scenario: Get country statistics by country
        When admin wants to see country statistics for 'ROM' between '2020-01-01' and '2020-01-04'
        Then the response is SUCCESSFUL
        And following country statistics are returned
            | date       | count |
            | 2020-01-01 | 2     |
            | 2020-01-04 | 1     |
