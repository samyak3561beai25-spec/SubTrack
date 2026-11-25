USE subtrack;

CREATE TABLE IF NOT EXISTS subscriptions (
    subscription_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    service_name VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    billing_cycle VARCHAR(20) NOT NULL,
    start_date DATE NOT NULL,
    renewal_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',

    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE
);
