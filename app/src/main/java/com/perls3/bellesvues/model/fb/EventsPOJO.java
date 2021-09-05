package com.perls3.bellesvues.model.fb;

public class EventsPOJO {
    private String description;
    private String event;
    private String eventImageUrl;

    public EventsPOJO() {
    }

    public EventsPOJO(String description, String event, String eventImageUrl) {
        this.description = description;
        this.event = event;
        this.eventImageUrl = eventImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    @Override
    public String toString() {
        return "EventsPOJO{" +
                "description='" + description + '\'' +
                ", event='" + event + '\'' +
                ", eventImageUrl='" + eventImageUrl + '\'' +
                '}';
    }
}
