package net.yoshinorin.orchard.models

case class DummyEvent() extends BaseEvent[DummyEvent] {
  override def insert(): Unit = ???
}
