package com.l.test.springboot.controller;

import com.l.test.springboot.enums.CardStatus;
import com.l.test.springboot.mapper.CardInfoMapper;
import com.l.test.springboot.mapper.CardRecordMapper;
import com.l.test.springboot.mapper.UserMapper;
import com.l.test.springboot.model.CardInfo;
import com.l.test.springboot.model.CardRecord;
import com.l.test.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardInfoMapper cardInfoDao;

    @Autowired
    private CardRecordMapper cardRecordMapper;

    @Autowired
    private UserMapper userDao;

    public void init(){
        try {
            List<User> users = userDao.selectAll();
        } catch (Exception e) {
            userDao.create();
            User u = new User();
            u.setUsername("猫驴子");
            u.setPassword("");
            userDao.insert(u);
            u.setUsername("钢蛋子");
            userDao.insert(u);
            cardInfoDao.create();
            cardRecordMapper.create();
        }
    }
    
    @RequestMapping("/home")
    public String  home(Model model) {
        init();
        User user = (User) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        List<CardRecord> cardRecords = cardRecordMapper.selectByUserId(user.getId());
        Map<String,Integer> currentCanUseCardRecordMap = new HashMap<>();
        AtomicLong currentCanUse = new AtomicLong();
        cardRecords.stream()
                .filter((c) ->  c.getStartTime().getTime() < new Date().getTime()
                        && c.getEndTime().getTime() > new Date().getTime()
                        && !c.isUsed())
                .forEach((cr) -> {
                    currentCanUse.getAndIncrement();
                    Integer count = currentCanUseCardRecordMap.get(cr.getCardName());
                    if(null==count){
                        currentCanUseCardRecordMap.put(cr.getCardName(),1);
                    }else{
                        currentCanUseCardRecordMap.put(cr.getCardName(),count+1);
                    }
                });
        long used = cardRecords.stream()
                .filter((cr) -> cr.isUsed()).count();
        model.addAttribute("used",used);
        model.addAttribute("unused",cardRecords.size()-used);
        model.addAttribute("currentCanUse",currentCanUse);
        model.addAttribute("currentCanUseCardRecordMap",currentCanUseCardRecordMap);
        return "card/index";
    }

    @RequestMapping("/type/list")
    public String  tyepList(Model model) {
        List<CardInfo> cardInfos = cardInfoDao.selectAll();
        model.addAttribute("cardInfos",cardInfos);
        return "card/type-list";
    }

    @RequestMapping("/type/add")
    public String  add(Model model) {
        return "card/add-new-card";
    }

    @RequestMapping("/record/send")
    public String  sendCard(Model model) {
        List<User> users = userDao.selectAll();
        System.out.println(users.size());
        users.stream().forEach((d) -> System.out.println(d));
        List<CardInfo> cardInfos = cardInfoDao.selectAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        model.addAttribute("users",users);
        model.addAttribute("cardInfos",cardInfos);
        return "card/send-card";
    }

    @RequestMapping("/record/send_submit")
    public String  sendCardSubmit(int receiverId,int cardId,String sendReason,Model model) {
        CardRecord cardRecord = new CardRecord();
        cardRecord.setOwner(receiverId);
        cardRecord.setCardId(cardId);
        cardRecord.setCreateTime(new Date());
        cardRecord.setStartTime(new Date());
        cardRecord.setEndTime(new Date(System.currentTimeMillis()+888888888));
        cardRecord.setSendReason(sendReason);
        cardRecord.setUseReason("");
        cardRecord.setUsed(false);
        cardRecord.setUseTime(new Date());
        cardRecordMapper.insert(cardRecord);
        return "card/send-card";
    }

    @RequestMapping("/type/add_submit")
    public String  addSubmit(String name, String rules, @RequestParam(required = false) String cardStatus, String backgroundColor, Model model) {
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(name)){
            throw new RuntimeException("填写信息不允许为空");
        }
        CardInfo cardInfo = new CardInfo();
        cardInfo.setName(name);
        cardInfo.setRules(rules);
        CardStatus cardStatus1 = CardStatus.OFF;
        if(null!=cardStatus&&"ON".equals(cardStatus)){
            cardStatus1 = CardStatus.ON;
        }
        cardInfo.setProductorId("1234536");
        cardInfo.setCardStatus(cardStatus1);
        cardInfo.setBackgroundColor(backgroundColor);

        System.out.println(cardInfo);
        cardInfoDao.insert(cardInfo);
        return this.tyepList(model);
    }

}
