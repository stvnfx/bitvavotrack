# Crypto Trading Data

This is a cryptocurrency trading data application that collects data from the Bitvavo exchange. It uses a WebSocket to collect real-time trading data and a REST API for other market information. The data is stored in a DuckDB database.

## Architecture

The application is built with Java and Quarkus. It consists of the following main components:

- **WebSocket Client:** Connects to the Bitvavo WebSocket API to receive real-time trade data.
- **REST Client:** Connects to the Bitvavo REST API to fetch additional market data.
- **Panache Repositories:** Used for data persistence with a DuckDB database.
- **Ta4j:** A library for technical analysis, used for backtesting features.

## How to Set Up

To set up the project, you need to have the following installed:

- Java 21 or later
- Maven 3.8.1 or later


## How to Run

To run the application, use the following Maven command:

```bash
./mvnw quarkus:dev
```

The application will be available at http://localhost:8080.

## Database

The application uses DuckDB for data storage. The database file is located in the `data/` directory. The database is created automatically when the application is started for the first time.

## Future Developments

The project has a long-term goal of migrating from DuckDB to PostgreSQL with the TimescaleDB extension. This will allow for better integration with Grafana for data visualization.
