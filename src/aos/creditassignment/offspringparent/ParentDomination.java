/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.creditassignment.offspringparent;

import aos.creditassigment.AbstractOffspringParent;
import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.ParetoDominanceComparator;

/**
 * This credit definition compares offspring to its parents
 *
 * @author Nozomi
 */
public class ParentDomination extends AbstractOffspringParent {

    /**
     * Credit that is assigned if the offspring dominates parent
     */
    private final double creditOffspringDominates;

    /**
     * Credit that is assigned if the parent dominates offspring
     */
    private final double creditParentDominates;

    /**
     * Credit that is assigned if neither the offspring or parent dominates the
     * other
     */
    private final double creditNoOneDominates;

    private final ParetoDominanceComparator comp;

    /**
     * Constructor to specify the amount of reward that will be assigned and the
     * dominance comparator to be used
     *
     * @param rewardOffspringDominates Reward that is assigned if the offspring
     * dominates parent
     * @param rewardParentDominates Reward that is assigned if the parent
     * dominates offspring
     * @param rewardNoOneDominates Reward that is assigned if neither the
     * offspring or parent dominates the other
     */
    public ParentDomination(double rewardOffspringDominates, double rewardNoOneDominates, double rewardParentDominates) {
        super();
        this.creditOffspringDominates = rewardOffspringDominates;
        this.creditParentDominates = rewardParentDominates;
        this.creditNoOneDominates = rewardNoOneDominates;
        this.comp = new ParetoDominanceComparator();
    }

    @Override
    public double compute(Solution offspring, Solution parent) {
        switch (comp.compare(offspring, parent)) {
            case -1:
                return creditOffspringDominates;
            case 0:
                return creditNoOneDominates;
            default:
                return creditParentDominates;
        }
    }
}