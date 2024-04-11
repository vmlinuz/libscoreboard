# Live Football World Cup Scoreboard Library

This library provides a simple in-memory solution for tracking live football World Cup matches and their scores. It supports starting new matches, updating scores, finishing matches, and getting a summary of matches in progress.

## Table of Contents
1. [Features](#features)
2. [Setup](#setup)
3. [Usage](#usage)
4. [Running the Tests](#running-the-tests)
5. [Integration](#integration)
6. [Assumptions and Limitations](#assumptions-and-limitations)
7. [License](#license)

## Features

1. **Start a new match**: Starts a new match with the given home and away teams, and an initial score of 0 - 0.
2. **Update score**: Updates the score of the match between the given home and away teams.
3. **Finish match**: Finishes the match between the given home and away teams, removing it from the scoreboard.
4. **Get a summary of matches**: Returns a list of match summaries, ordered by total score and then by start time.

## Setup

To set up the library, you need to have **Java 21** and recent version of Maven installed on your system. Once you have these prerequisites, you can clone the repository to your local machine and run the following command at the root folder of the project:

```bash
mvn clean install
```

This command cleans the project, compiles the source code, runs the tests, and installs the compiled code to your local Maven repository.  
After running this command, you should see a `BUILD SUCCESS` message in your terminal, indicating that the project has been successfully built.

## Usage

Here's an example of how you can use the library in your code:

```java
import org.example.Scoreboard;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();

        // Start a new match
        scoreboard.startMatch("HomeTeam1", "AwayTeam1");

        // Update the score
        scoreboard.updateScore("HomeTeam1", "AwayTeam1", 1, 0);

        // Get a summary of matches
        List<String> summary = scoreboard.getSummary();
        summary.forEach(System.out::println);

        // Finish the match
        scoreboard.finishMatch("HomeTeam1", "AwayTeam1");
    }
}
```

## Running the Tests

To run the unit tests for the library, run the following command at the root folder of the project:

```bash
mvn test
```

Unit tests are provided in the `ScoreboardTest` class in the `src/test/java/org/example` directory. These tests verify the functionality of the library using the JUnit 5 framework.

## Integration

To integrate the library into another Maven project, you can install it to your local Maven repository using the `mvn install` command and then add it as a dependency in your project's `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.example</groupId>
        <artifactId>libscoreboard</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```

## Assumptions and Limitations

- **Team names**: Team names are assumed to be non-null and non-empty.
- **Scores**: Scores are assumed to be non-negative integers.
- **Unique Matches**: Matches are uniquely identified by their home and away teams.
- **Multithreading**: The current implementation of the library is not thread-safe. If multiple threads attempt to modify the scoreboard concurrently, it may lead to inconsistent state. Therefore, it is not recommended to use this library in a multithreaded environment without proper synchronization.
- **Scalability**: The library is designed for simplicity and is best suited for small to medium-sized applications. For large-scale applications with high throughput, a more robust and scalable solution may be needed.
- **Persistence**: The library uses an in-memory data store and does not support persistence. If the application is shut down, all data will be lost.
- **Logging**: The library does not include logging functionality. In a production environment, logging would be essential for monitoring and troubleshooting the application.
- **Exception Handling**: In the test cases, exceptions are being thrown and caught, but there's no custom exception handling. In future custom exceptions, that extend from RuntimeException or Exception to provide more specific error messages, can be created.
- **Internationalization**: The library does not support internationalization or localization. In a production environment, it would be important to provide support for multiple languages and regions.
- **Performance**: The library is designed for simplicity and ease of use, rather than performance optimization. In a production environment, it would be important to profile the application and optimize performance where necessary.

## License

This project is licensed under the MIT License.
