-- Create views
CREATE OR REPLACE VIEW finance_stats_overview AS
    SELECT
        (1) AS id,
        IFNULL((SELECT COUNT('') FROM finance_txs), 0) AS totalTxs,
        IFNULL((SELECT SUM(amount) FROM finance_txs WHERE amount < 0), 0) AS totalOut,
        IFNULL((SELECT SUM(amount) FROM finance_txs WHERE amount > 0), 0) AS totalIn,
        IFNULL((SELECT SUM(amount) FROM finance_txs), 0) AS totalBalance
    ;

CREATE OR REPLACE VIEW finance_stats_monthly AS
    SELECT
        CONCAT(
            YEAR(FROM_UNIXTIME(ft.epochDateTime/1000)),
            MONTH(FROM_UNIXTIME(ft.epochDateTime/1000))
        ) AS id,
        YEAR(FROM_UNIXTIME(ft.epochDateTime/1000)) AS year,
        MONTH(FROM_UNIXTIME(ft.epochDateTime/1000)) AS month,
        COUNT('') AS totalTxs,
        SUM(ft.amount > 0) AS totalTxsIn,
        SUM(ft.amount < 0) AS totalTxsOut,
        SUM(CASE WHEN ft.amount > 0 THEN ft.amount ELSE 0 END) AS totalIn,
        SUM(CASE WHEN ft.amount < 0 THEN ft.amount ELSE 0 END) AS totalOut,
        (SELECT SUM(ft2.amount) FROM finance_txs AS ft2 WHERE ft2.epochDateTime <= ft.epochDateTime) AS balance
    FROM finance_txs AS ft
    GROUP BY
        MONTH(FROM_UNIXTIME(ft.epochDateTime/1000)),
        YEAR(FROM_UNIXTIME(ft.epochDateTime/1000))
    ORDER BY
        year DESC,
        month DESC
    ;

