Feature: Reset functionality on login page of Application


Scenario Outline: Verification of reset button with different credentials


Given Open chrome and launch the application


When Enter the username and password "<username>"" <password>"


    Then Reset the credential

    Examples:

    |username  |password         |

    |User1     |password1        |

    |User2     |password2        |

    |User3     |password3        |