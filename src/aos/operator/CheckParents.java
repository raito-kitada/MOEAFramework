/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operator;

import org.moeaframework.core.Solution;

/**
 * This class is for variation operators that should check the parents that is
 * assigned to it before operating. Example situations when such a method is
 * useful is when using knowledge-dependent operators that should check a
 * certain condition is met. Otherwise, the parents might not undergo any
 * change but still would be evaluated, which wastes an evaluation.
 *
 * @author nozomihitomi
 */
public interface CheckParents {

    /**
     * Checks the solution and determines whether the parents meet some
     * internal criteria
     *
     * @param parents the parents to check
     * @return true if the parents meet the criteria, else false.
     */
    public boolean
            check(Solution[] parents);
}