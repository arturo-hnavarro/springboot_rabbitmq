Document based on https://spring.io/blog/2010/06/14/understanding-amqp-the-protocol-used-by-rabbitmq

Exchanges, queues, and bindings

Message broker is in charge of connecting publisher with consumers.
Broker uses exchanges and queues.
A publisher sends messages to a named exchange and a consumer pulls messages from a queue.
  Queue must be linked to an exchange. This is done by the consumer. The consumer creates a queue, that queue is linked to an exchange.
  Messages received by the exchange have to be matched to the queue - a process called "binding".

  How Binding works?
  AMQP messages contains Headers (similar to HTTP calls).  routing-key is a header that broker uses to know to which queue the message belongs to.
  Each queue specifies a "binding key" and if that key matches the value of the routing-key header, the queue receives the message.
  if "queue binding key" is equals "routing-key header" then, message is sent to that queue.

  The AMQP spec. defines four types of exchanges:
    1. Direct:	The binding key must match the routing key exactly.
    2. Topic: Same as Direct, but wildcards are allowed in the binding key. '#' matches zero or more dot-delimited words and '*' matches exactly one such word.
    3. Fanout: all published messages go to all bound queues. The routing and binding keys are ignored.
    
Esto es un nuevo Feature de actualizacion de la documentacion del proyecto. Estoy creando un nuevo Feature con Git-flow