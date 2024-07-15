# Russia Report Signature Appender

## Overview

The Russia Report Signature Appender is a Java-based application designed to append digital signatures to XML reports in compliance with Russian pharmaceutical regulations. This tool ensures that reports are authenticated and meet the requirements of the MDLP.

## Features

- **Digital Signature Addition**: Automatically downloads the files pending signature, creates a signature and responds back to tracelink with the signed copy of the report.
- **Configuration**: Easily configurable via a properties file.
- **Compatibility**: Compatible with Java environments.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven (for building the project)
- Access to digital certificates compliant with Russian regulations

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/tracelink/russia-report-signature-appender.git
   cd russia-report-signature-appender
2. **Build the Project:**
   ```bash
   mvn clean install

## Configuration

- **Edit the application.config file (Sample file below):**
   ```bash
   username=<username>
   password=<password>
   baseUri=<ru application url>
   tokenPath=/api/token
   taskPath=/api/tasks
   taskResultPath=/api/taskResult
   pathForExecutable=c:\\Program Files\\Crypto Pro\\CSP\\csptest.exe
   inputFilePath=C:\\Users\\user\\Downloads\\Demo\\telo.txt
   outputFilePath=C:\\Users\\user\\Downloads\\Demo\\sign_out.txt
   generateSignatureFrequency=1
   log.level=debug
   failReportOnSignatureFailure=true

Note : Sample folder provided in the repo.

## Usage
- **Run the application:**
   ```bash
   java -jar target/<jar-name> <complete path of application.config file>

## Dependencies
1. Windows Server 2019 or above
2. CryptoPro CSP 5.0.11732 and above
3. Java 8 or above

## Contributing
1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature-branch).
5. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE file](https://github.com/tracelink/russia-report-signature-appender/blob/main/LICENSE) for details.

## Support
For any questions or issues, please open an issue on the [GitHub repository](https://github.com/tracelink/russia-report-signature-appender/issues).
