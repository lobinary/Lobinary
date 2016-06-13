package com.lobinary.platform.service.team;

import java.util.ArrayList;
import java.util.List;

import javax.mail.SendFailedException;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lobinary.platform.model.db.team.Teamer;
import com.lobinary.platform.service.BaseService;
import com.lobinary.platform.util.LogUtil;
import com.lobinary.platform.util.MailUtil;

/**
 * 团队业务类
 * @author Lobinary
 *
 */
@Service("teamService")
public class TeamService  extends BaseService {
	public Logger logger = LogUtil.getLog(getClass());

	@Transactional(readOnly = false)
	public boolean add(final Teamer t) {
		new Thread() {
			public void run() {
				try {
					List<Teamer> mailToListTeamer = queryListByPage(Teamer.class, false, 1, 0, 1000, null);
					List<String> mailToList = new ArrayList<String>();
					for (Teamer teamer : mailToListTeamer) {
						String email = teamer.getEmail();
						if (email != null && email.contains("@")) {
							mailToList.add(email);
						}
					}
					MailUtil.sendMail(
							mailToList,
							"欢迎新队友加入我们团队",
							"欢迎新队友 " + t.getName() + " 加入我们团队,TA的个人信息如下：\n"
									+ t.toMailString());
					if (t.getEmail() != null && t.getEmail().contains("@")) {
						MailUtil.sendMail(t.getEmail(), "欢迎您加入疯子团队",
								"欢迎您加入疯子团队，这个账号为疯子团队系统账号，如有问题，可以发送邮件到这个账号，您的个人信息已经被疯子团队保存成功，具体信息如下：\n"
										+ t.toMailString());
					}
				} catch (SendFailedException e) {
					logger.info("新同事通知邮件发送失败");
				}
			}
		}.start();
		return super.add(t);
	}
	
	

}
