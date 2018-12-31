package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class ForkEvents(
  eventId: Long,
  userName: String,
  forkedRepositoryId: Long,
  createdAt: Long
) extends BaseEvent[ForkEvents]
