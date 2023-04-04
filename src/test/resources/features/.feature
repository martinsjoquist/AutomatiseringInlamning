Feature: Register users at Mailchimp.com

  Scenario Outline: Create users at Mailchimp.com
    Given I choose a "<browser>" and brows to Mailchimp.com
    Given I enter a "<email>" adress
    And I type a "<userName>" and a "<password>"
    Then I click the sign up button and a new user is "<created>"

    Examples:
      | browser | email        | userName     | password   | created |
      | Chrome  | yep@yep.com  | YepMartin    | Yepp12345? | work    |
      | Edge    | yep@yep.com  | YepMartin    | Yepp12345? | work    |
      | Chrome  | yep@yep.com  | Miguel       | Yepp12345? | no      |
      | Edge    | yep@yep.com  | Miguel       | Yepp12345? | no      |
      | Chrome  | yep1@yep.com | lol1@lol.com | Yepp12345? | fail    |
      | Edge    | yep1@yep.com | lol1@lol.com | Yepp12345? | fail    |
      | Chrome  | 123          | YepMartin    | Yepp12345? | nope    |
      | Edge    | 123          | YepMartin    | Yepp12345? | nope    |
