package br.com.cwi.crescer.api.service.quest;

import br.com.cwi.crescer.api.domain.Quest;
import br.com.cwi.crescer.api.domain.User;
import br.com.cwi.crescer.api.domain.UserQuest;
import br.com.cwi.crescer.api.repository.UserQuestRepository;
import br.com.cwi.crescer.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SuccessCompleteQuestService {
    @Autowired
    private UserQuestRepository userQuestRepository;

    @Autowired
    private UserRepository userRepository;

    public void complete(User user, UserQuest userQuest, Quest quest) {
        userQuest.setCompletedAt(LocalDate.now());
        user.setMoney(user.getMoney() + quest.getRewards());
        user.setExperience(user.getExperience() + quest.getExperience());
        userRepository.save(user);
        userQuestRepository.save(userQuest);
    }
}
