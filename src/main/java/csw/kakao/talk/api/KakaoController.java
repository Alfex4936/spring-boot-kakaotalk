package csw.kakao.talk.api;

import csw.kakao.talk.json.Template;
import csw.kakao.talk.json.Template.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
public class KakaoController {

    @PostMapping("/process")
    public ResponseEntity<Template> processOptionalJson(@RequestBody(required = false) Map<String, Object> inputData) {
        // Process inputData if necessary
        System.out.println(inputData);

        Template template = new Template();

        Button textButton = Button.newTextButton("text label");
        Button linkButton = Button.newLinkButton("link label", "https://google.com");
        Button shareButton = Button.newShareButton("share label");
        Button callButton = Button.newCallButton("call label", "010-1234-5678");

        Header header = new Header();
        header.setTitle("리스트 카드 제목!");

        Item item = new Item();
        item.setTitle("title");
        item.setDescription("description");
        item.setLink(Collections.singletonMap("web", "https://naver.com"));

        ListCard listCard = new ListCard();
        listCard.setButtons(new ArrayList<>(Arrays.asList(textButton, linkButton)));
        listCard.addbutton(shareButton);
        listCard.addbutton(callButton);
        listCard.setHeader(header);
        listCard.setItems(Collections.singletonList(item));

        template.addOutput(new SimpleText("Hello"));
        template.addOutput(listCard);

        QuickReply quickReply1 = new QuickReply();
        quickReply1.setAction("message");
        quickReply1.setLabel("오늘");
        quickReply1.setMessageText("오늘 공지 보여줘");

        template.addQR(quickReply1);

        return ResponseEntity.ok(template);
    }
}
