/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos.operator;

import aos.aos.SerializableVal;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variation;
import org.moeaframework.core.operator.CompoundVariation;

/**
 * This variation is a wrapper for other variations and can take on their
 * methods. The wrapped variation can be changed using the set method
 *
 * @author nozomihitomi
 */
public class AOSVariation implements Variation {

    private Variation var;

    private Solution[] parents;

    private Solution[] offspring;

    @Override
    public int getArity() {
        return var.getArity();
    }

    @Override
    public Solution[] evolve(Solution[] parents) {
        this.parents = parents;
        this.offspring = var.evolve(parents);
        return this.offspring;
    }

    public Solution[] getParents() {
        return parents;
    }

    public Solution[] getOffspring() {
        return offspring;
    }

    public void setVariation(Variation variation) {
        this.var = variation;
    }

    public Variation getVariation(Variation variation) {
        return var;

    }
}