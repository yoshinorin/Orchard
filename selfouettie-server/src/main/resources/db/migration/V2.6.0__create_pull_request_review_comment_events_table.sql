CREATE TABLE pull_request_review_comment_events (
  event_id BIGINT UNSIGNED PRIMARY KEY,
  repository_id BIGINT UNSIGNED NOT NULL,
  pull_request_number BIGINT UNSIGNED NOT NULL,
  action ENUM ('created','edited','deleted'),
  created_at DATETIME DEFAULT NULL,
  FOREIGN KEY(event_id) REFERENCES events(id),
  FOREIGN KEY(repository_id) REFERENCES repositories(id)
) DEFAULT CHARSET=utf8mb4;