package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class PullRequestReviewEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Int,
  action: String,
  createdAt: Long
) extends BaseEvent[PullRequestReviewEvents]
