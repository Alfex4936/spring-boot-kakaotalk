package csw.kakao.talk.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;


@Data
@JsonSerialize(using = Template.Serializer.class)
public class Template {
    private String version = "2.0";

    private List<Output> outputs = null;
    private List<QuickReply> quickReplies = null;

    public void addOutput(Output output) {
        if (outputs == null) {
            outputs = new ArrayList<>();
        }
        outputs.add(output);
    }

    public Template addQR(QuickReply qr) {
        if (quickReplies == null) {
            quickReplies = new ArrayList<>();
        }
        quickReplies.add(qr);
        return this;
    }

public static class Serializer extends JsonSerializer<Template> {
    @Override
    public void serialize(Template value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("version", value.getVersion());
        gen.writeObjectFieldStart("template");
        gen.writeObjectField("outputs", value.getOutputs());


        gen.writeEndObject();
        gen.writeEndObject();
    }
}


    @Data
    public static class ListCardInner {
        private ArrayList<Button> buttons;
        private Header header;
        private ArrayList<Item> items;

        public ListCardInner() {
            this.buttons = new ArrayList<>();
            this.items = new ArrayList<>();
        }
    }

    @Data
    public static class SimpleTextInner {
        private String text;

        public SimpleTextInner(String text)  {
            this.text = text;
        }
    }

    @Data
    public static class Button {
        private String label;
        private String action;
        private String webLinkUrl;
        private String messageText;
        private String phoneNumber;

        // constructor
        public Button() {
        }

        public static Button newTextButton(String label) {
            Button button = new Button();
            button.setAction("message");
            button.setLabel(label);
            return button;
        }

        public static Button newLinkButton(String label, String url) {
            Button button = new Button();
            button.setAction("webLink");
            button.setLabel(label);
            button.setWebLinkUrl(url);
            return button;
        }

        public static Button newShareButton(String label) {
            Button button = new Button();
            button.setAction("share");
            button.setLabel(label);
            return button;
        }

        public static Button newCallButton(String label, String number) {
            Button button = new Button();
            button.setAction("phone");
            button.setLabel(label);
            button.setPhoneNumber(number);
            return button;
        }

        public void setWebLinkUrl(String url) {
            this.webLinkUrl = url;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public void setPhoneNumber(String number) {
            this.phoneNumber = number;
        }

        // getters and setters
    }

    @Data
    public static class Header {
        private String title;
    }

    @Data
    public static class Item {
        private String title;
        private String description;
        private Map<String, String> link;
    }

    @Data
    public static class QuickReply {
        private String action;
        private String label;
        private String messageText;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = ListCard.class, name = "listCard"),
            @JsonSubTypes.Type(value = SimpleText.class, name = "simpleText")
    })
    public abstract static class Output {
        // remove the property definitions
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ListCard extends Output {
        private ListCardInner listCard;

        public ListCard() {
            this.listCard = new ListCardInner();
        }

        public void addbutton(Button btn) {
            this.listCard.buttons.add(btn);
        }

        public void setButtons(ArrayList<Button> textButton) {
            this.listCard.buttons = textButton;
        }

        public void setHeader(Header header) {
            this.listCard.header = header;
        }

        public void setItems(List<Item> items) {
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class SimpleText extends Output {
        private SimpleTextInner simpleText;

        public SimpleText(String text) {
            this.simpleText = new SimpleTextInner(text);

        }

        public SimpleText newSimpleText(String text) {
            this.simpleText.text = text;
            return this;
        }
    }
}
