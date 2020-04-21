package cn.gingo.global.result.handler.checkers;

import java.util.ArrayList;
import java.util.List;

public class SupportCheckerHolder {

    private List<SupportChecker> supportCheckers = new ArrayList<>();

    public SupportCheckerHolder() {
        supportCheckers.add(new DefaultSupportChecker());
    }

    public void addSupportChecker(SupportChecker supportChecker) {
        this.supportCheckers.add(supportChecker);
    }

    public List<SupportChecker> getSupportCheckers() {
        return this.supportCheckers;
    }
}
