package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsMotherDoesNotHaveFeverRule;

import static org.opensrp.common.util.EasyMap.create;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class IsMotherDoesNotHaveFeverRuleTest {
    private IsMotherDoesNotHaveFeverRule rule;

    @Before
    public void setUp() throws Exception {
        rule = new IsMotherDoesNotHaveFeverRule();
    }

    @Test
    public void shouldReturnTrueIfMotherDoesNotHaveFever() {
        boolean didRuleApply = rule.apply(new SafeMap(create("hasFever", "").map()));
        assertTrue(didRuleApply);
    }

    @Test
    public void shouldReturnFalseIfMotherHasFever() {
        SafeMap safeMap = new SafeMap(create("hasFever", "High fever").map());

        boolean didRuleApply = rule.apply(safeMap);
        assertFalse(didRuleApply);
    }
}
