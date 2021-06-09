# Team_27


**Anti-Covid-App** 


# INTRODUCTION

Anti-Covid-App is a mobile application which carries your Corona-Test information in form of a QR code and helps with fighting the pandemic.

The app provides functionality to create users profile with their personal information, which is verified either with ID card or it is validated in person by specific institutions. 
It is advised that we all, as citizens, should test ourselves as much as possible. Following those advice , the users will have opportunity to hold all their test results inside of one app.
Based on type of the test ( pharmacy, PCR, antigen…) the specific QR code will be created which is valid for some period of time ( PCR test is valid for next 72 hours, pharmacy for 48 hours). If the user is vaccinated, permanent QR code will be generated. 
In case of a positive test, users will be notified with further informations about quarantine and they will be able to list all the contacts they had.
This app also provides users with official institution rules and changes about current situation ( overall corona situation in the country, statistics, new infections, number of tests, travelling information, general information about the things you can do and the things which are prohibited by the government). No fake news, just official!


## Team
| Role | Name |
| ---- | ---- |
| **Product Owner** | Kohl, Andreas |
| **Scrum Master** | Tögl, Christina |
| **Developer** | Brugger, Lukas |
| **Developer** | Csordás, Bálint |
| **Developer** | Lemle, Béla |
| **Developer** | Nagler, Gabriel |
| **Developer** | Scheiber, Dominik |
| **Developer** | Schrimpf, Simon Wolfgang |
| **Developer** | Stangl, Tobias |
| **Developer** | ~~Tarkhanova, Elizaveta~~ |
| **Product Owner** | ~~Alesevic, Muharem~~ |
| **Developer** | ~~Ghiriti, Alex-Ioan~~ |

## Implemented Features
| User Story | Feature |
| ---- | ---- |
| [ANTIC-001](https://github.com/sw21-tug/Team_27/issues/1) | User registration |
| [ANTIC-003](https://github.com/sw21-tug/Team_27/issues/3) | Login |
| [ANTIC-004](https://github.com/sw21-tug/Team_27/issues/4) | Logout |
| [ANTIC-006](https://github.com/sw21-tug/Team_27/issues/6) | Notification case negative |
| [ANTIC-007](https://github.com/sw21-tug/Team_27/issues/7) | Vaccine ID |
| [ANTIC-008](https://github.com/sw21-tug/Team_27/issues/8) | Terms of use |
| [ANTIC-009](https://github.com/sw21-tug/Team_27/issues/9) | Editing account infos |
| [ANTIC-010](https://github.com/sw21-tug/Team_27/issues/10) | Switching between screens / Home Screen |
| [ANTIC-011](https://github.com/sw21-tug/Team_27/issues/11) | Basic App Setup |
| [ANTIC-012](https://github.com/sw21-tug/Team_27/issues/12) | Basic Git Setup |
| [ANTIC-013](https://github.com/sw21-tug/Team_27/issues/13) | Firebase SDK |
| [ANTIC-014](https://github.com/sw21-tug/Team_27/issues/14) | Create QR Code |
| [ANTIC-023](https://github.com/sw21-tug/Team_27/issues/41) | Refactor Registration  |
| [ANTIC-024](https://github.com/sw21-tug/Team_27/issues/42) | Change language to Chinese |
| [ANTIC-025](https://github.com/sw21-tug/Team_27/issues/43) | Research for User Statistics |
| [ANTIC-027](https://github.com/sw21-tug/Team_27/issues/46) | Add Test Report |
| [ANTIC-029](https://github.com/sw21-tug/Team_27/issues/50) | Refactor Correct data in QR-Code |
| [ANTIC-031](https://github.com/sw21-tug/Team_27/issues/52) | Refactor Home-Screen |
| [ANTIC-032](https://github.com/sw21-tug/Team_27/issues/53) | Delete Test Reports |
| [ANTIC-033](https://github.com/sw21-tug/Team_27/issues/54) | Corona statistic for all countries |
| [ANTIC-034](https://github.com/sw21-tug/Team_27/issues/55) | Refactor Create Account |
| [ANTIC-035](https://github.com/sw21-tug/Team_27/issues/66) | Redesign of Home-Screen |
| [ANTIC-036](https://github.com/sw21-tug/Team_27/issues/68) | Change "My vaccine Information" |
| [ANTIC-037](https://github.com/sw21-tug/Team_27/issues/69) | QR-Code add vaccinaded as additional information |
| [ANTIC-038](https://github.com/sw21-tug/Team_27/issues/73) | Refactor Update Input Fields to State of the Art |
| [ANTIC-039](https://github.com/sw21-tug/Team_27/issues/74) | Refactor Get UI on the same design. (character size etc.) |
| [ANTIC-040](https://github.com/sw21-tug/Team_27/issues/78) | Refactor "Add Test Report"-Screen into Test Reports |
| [ANTIC-041](https://github.com/sw21-tug/Team_27/issues/79) | Refactor "QR Code"-Screen into Test Report |
| [ANTIC-042](https://github.com/sw21-tug/Team_27/issues/81) | Move "Change Password" to Profile |
| [ANTIC-043](https://github.com/sw21-tug/Team_27/issues/84) | Filter for Test Reports |
| [ANTIC-044](https://github.com/sw21-tug/Team_27/issues/85) | Refactor Statistic Screen Design |
| [ANTIC-045](https://github.com/sw21-tug/Team_27/issues/88) | Refactor Logout and Save Icons |
| [ANTIC-046](https://github.com/sw21-tug/Team_27/issues/89) | Bugfix Logout Button |
| [ANTIC-047](https://github.com/sw21-tug/Team_27/issues/92) | Bugfix should work on smartphone |
| [ANTIC-048](https://github.com/sw21-tug/Team_27/issues/93) | Bugfix Country FlagANTIC-050 |
| [ANTIC-050](https://github.com/sw21-tug/Team_27/issues/99) | Bug Vaccine Info not editable |
| [ANTIC-051](https://github.com/sw21-tug/Team_27/issues/104) | Refactor Log in Error Messages |
| [ANTIC-054](https://github.com/sw21-tug/Team_27/issues/107) | Refactor E-Mail field in Profile |
| []() |  |

# Description of Features:

This is a Covid-19-App where you can save your corona tests and show them to people via QR-Code. But lets start from the beginning.
At first you have to create an account for the app. You can change your personal data later in the profile window.
The first thing you will see if you log in into the app is the statistics screen. This screen is showing you latest corona statistic for countries.
Next window is the test reports window. Here you have all your test reports from corona tests. You can sort them by validity. You can delete old ones and add new ones with the +
button. If you add a new test report the date is filled in automatically and if the test you entered is positive you can get information on what to do next.
You can also click on the test reports individually to get the QR code. In the vaccine information screen you can add your vaccination. This will be added in the QR code as 
well. In the profile window you can change the language to chinese. You can also change your password.



