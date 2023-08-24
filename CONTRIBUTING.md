# Contribute to SE310 G6 Budget tracker 
We'd welcome you to contribute to improve our budget tracker! Here're some guidelines we'd like you to follow.
- [The high level design](#highLv)
- [Project roadmap](#projRM)
- [Bug report](#bugrep)
- [Adding feature](#newfeat)
- [Setting up](#setup)
- [Testing](#test)
- [Expected contributions](#excon)
## High level design
The aim of the Budget Tracker project is to create a personal finance web application to enhance user’s financial awareness and to promote healthy financial spending habits. By providing a set of user-friendly and intuitive tools, users will be able to seamlessly track their expenses, set personalised budgets, and visualise their spending patterns, empowering them to make informed financial decisions and work towards their financial goals.
## Project roadmap

This project consists of 3 development stages, each with different scope of work

#### Stage 1 focus:
- Setting budgets/savings goals
- Categorising and tracking of expenses
- Spending overflow notification
- Visualisation of spending/ spending reports

#### Stage 2 focus:
- Forecast/extrapolate different spending and expenses inputs into future
- Customizability of spending reports
- Allow users to export their expenses data and their reports for easier record keeping

#### Stage 3 focus:
- Collaborative Budgeting: Implement a feature that enables multiple users (e.g., families or project teams) to collaborate on a shared budget and track expenses collectively.
- Different user roles with different permissions
- payment service integration and bill splitting (Potential*)


## Found a problem?
If you find bug in the source code or mistakes in documentation, you can help us by submitting [an issue](https://github.com/nathanbell806/310A1BudgetTracker/issues/new?assignees=&labels=bug&projects=&template=bug_report.md&title=) along with a bug report

You'll be asked to include the following in the bug report:

- Observed/Expected behaviors
- Steps to reproduce
- Other necessary info such as error reports, screenshots, build condition

## Adding features

You can request a new feature by submitting [an issue](https://github.com/nathanbell806/310A1BudgetTracker/issues/new?assignees=&labels=&projects=&template=feature_request.md&title=).

Make sure you search for existing issues before you make a new one!

You should fork this repository and implement the feature on a new branch. 

Once you finished the implementation, create a pull request that reference to the resolved issue with a description of changes made. Your pull request should be reviewed by another contributor before being approved.

## Setting up 
Priquisities:
- [Java JDK 17.0.2](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
JavaFX should be installed by the Maven setup in this project

### Installation
1. Configure the JAVA_HOME environment variable by following the instructions [here](https://www.java.com/en/download/help/path.html). Afterwards, verify if the JAVA_HOME environment variable is properly set by navigating to your Terminal and typing the following:
   ```bash
   java -version
2. Once verified, clone the repository to your directory of choice:
   ```bash
   git clone https://github.com/nathanbell806/310A1BudgetTracker
3. Done!

### Running the project:
For Unix/MacOsX:  
`./mvnw clean javafx:run`

For Windows:  
`.\mvnw.cmd clean javafx:run`

## Testing
For Unix/MacOsX:  
`./mvnw clean test`

For Windows:  
`.\mvnw.cmd clean test`
## What is expected
Please checkout the code of conduct of this project: [Code of conduct](https://github.com/nathanbell806/310A1BudgetTracker/blob/main/CODE_OF_CONDUCT.md)
