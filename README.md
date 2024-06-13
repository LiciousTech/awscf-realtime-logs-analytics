# ClickHouse Distributed MergeTree Data Ingestion

This project provides a data ingestion pipeline that reads data from AWS Kinesis and writes it to a ClickHouse database using the Distributed MergeTree engine. The code is designed to handle multiple pods and threads, ensuring efficient and reliable data ingestion in a distributed environment.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Error Handling](#error-handling)
- [Testing and Validation](#testing-and-validation)
- [Contributing](#contributing)
- [License](#license)
- [Additional Files](#additional-files)

## Prerequisites

Before you begin, ensure you have the following installed:

- Java Development Kit (JDK) 11 or higher
- Apache Maven
- Docker
- Kubernetes (Minikube or any other Kubernetes setup)
- AWS CLI (configured with appropriate permissions for accessing Kinesis)

## Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/clickhouse-distributed-ingestion.git
   cd clickhouse-distributed-ingestion
   ```

2. **Build the project:**

   ```bash
   mvn clean install
   ```

3. **Dockerize the application:**

   ```bash
   docker build -t yourusername/clickhouse-distributed-ingestion:latest .
   ```

4. **Deploy ClickHouse on Kubernetes:**

   To deploy ClickHouse on Kubernetes using Helm, follow the [official documentation](https://clickhouse.tech/docs/en/operations/kubernetes/) provided by ClickHouse.

## Configuration

### Configure ClickHouse connection:

Update the `application.properties` file with your ClickHouse connection details:

```properties
clickhouse.url=jdbc:clickhouse://<your-clickhouse-host>:8123
clickhouse.username=<your-username>
clickhouse.password=<your-password>
```

### Configure AWS Kinesis:

Update the `kinesis.properties` file with your AWS Kinesis stream details:

```properties
kinesis.streamName=<your-stream-name>
aws.region=<your-aws-region>
aws.accessKeyId=<your-access-key-id>
aws.secretKey=<your-secret-key>
```

### ClickHouse Schema

Create the necessary tables in your ClickHouse cluster using the following schema:

```sql
CREATE TABLE cloudfront_logs (
    timestamp String,
    c_ip String,
    time_to_first_byte String,
    sc_status String,
    sc_bytes String,
    cs_method String,
    cs_protocol String,
    cs_host String,
    cs_uri_stem String,
    cs_bytes String,
    x_edge_location String,
    x_host_header String,
    cs_protocol_version String,
    c_ip_version String,
    cs_user_agent String,
    cs_referer String,
    cs_uri_query String,
    x_edge_response_result_type String,
    x_forwarded_for String,
    ssl_protocol String,
    x_edge_result_type String,
    sc_content_type String,
    c_country String,
    cs_accept_encoding String,
    cs_accept String,
    cache_behavior_path_pattern String,
    primary_distribution_id String,
    asn String
) ENGINE = Distributed('cluster_name', 'database_name', 'local_table_name', rand());
```

### Elasticsearch Schema

If you are also indexing data into Elasticsearch, create the necessary index with the provided mapping.

## Running the Application

1. **Deploy the application on Kubernetes:**

   ```bash
   kubectl apply -f app-deployment.yaml
   ```

2. **Monitor the application:**

   Check the logs of the running pods to ensure the application is processing data correctly:

   ```bash
   kubectl logs -f <your-pod-name>
   ```

## Error Handling

The application includes error handling mechanisms to manage common issues in distributed environments. If an error occurs during data ingestion, the application will log the error and attempt to retry the operation. Ensure that the ClickHouse cluster has enough resources (CPU, memory, and disk space) to handle the data load.

## Testing and Validation

### Unit Tests

Run the unit tests to ensure the core logic of the application is functioning correctly:

```bash
mvn test
```

### Integration Tests

Deploy the application in a test environment and validate that data is being ingested into the ClickHouse cluster correctly. Use ClickHouse's query interface to check the distribution and accuracy of the ingested data.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code adheres to the project's coding standards and includes appropriate tests.

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

## Additional Files

- **app-deployment.yaml**: Configuration for deploying the data ingestion application.
- **application.properties**: Configuration file for ClickHouse connection details.
- **kinesis.properties**: Configuration file for AWS Kinesis stream details.

Feel free to customize this README file as needed to match your specific project setup and requirements.
