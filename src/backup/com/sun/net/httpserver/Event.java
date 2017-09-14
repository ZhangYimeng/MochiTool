package backup.com.sun.net.httpserver;

class Event {

    ExchangeImpl exchange;

    protected Event (ExchangeImpl t) {
	this.exchange = t;
    }
}

class WriteFinishedEvent extends Event {
    WriteFinishedEvent (ExchangeImpl t) {
	super (t);
    }
}
