# Java Spring Boot

카카오톡 챗봇 서버 RESTful API 만들며 배우는 Java Spring

# 예제

```java
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
```

아래와 같은 JSON 파일 생성

```yaml
{
  "version": "2.0",
  "template": {
    "outputs": [
      {
        "simpleText": {
          "text": "Hello"
        }
      },
      {
        "listCard": {
          "buttons": [
            {
              "label": "text label",
              "action": "message"
            },
            {
              "label": "link label",
              "action": "webLink",
              "webLinkUrl": "https://google.com"
            },
            {
              "label": "share label",
              "action": "share"
            },
            {
              "label": "call label",
              "action": "phone",
              "phoneNumber": "010-1234-5678"
            }
          ],
          "header": {
            "title": "리스트 카드 제목!"
          }
        }
      }
    ]
  }
}
```