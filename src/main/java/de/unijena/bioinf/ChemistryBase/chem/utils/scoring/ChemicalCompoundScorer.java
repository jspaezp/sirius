package de.unijena.bioinf.ChemistryBase.chem.utils.scoring;


import de.unijena.bioinf.ChemistryBase.algorithm.ImmutableParameterized;
import de.unijena.bioinf.ChemistryBase.algorithm.ParameterHelper;
import de.unijena.bioinf.ChemistryBase.algorithm.Parameterized;
import de.unijena.bioinf.ChemistryBase.chem.MolecularFormula;
import de.unijena.bioinf.ChemistryBase.chem.utils.MolecularFormulaScorer;
import de.unijena.bioinf.ChemistryBase.data.DataDocument;

/**
 * Factory for chemical scorers
 */
public final class ChemicalCompoundScorer {

    private ChemicalCompoundScorer() {
    }

    /**
     * creates a scorer for molecular formulas of the compound. This scorer should not be used
     * for fragments! Currently, the following scorings are used:
     * 1. Hetero-to-Carbon ratio: Heteroatoms are all atoms which are not carbon, hydrogen or oxygen(!)
     * 2. Some compounds have an oxygen+hetero backbone. For this compound, the SpecialMoleculeScorer is used:
     * It scores the Oxygen-to-hetero ratio as well as the RDBE value
     * <p/>
     * From both scorings the maximum is used
     * Parameters: All distributions are Uniform+Pareto-Distributions. For all x0<x<xmin the probability is uniform and
     * maximal. For all x>xmin the probability decreases according to a pareto distribution by parameter k
     * Hetero-to-carbon: x0=0, xmin=1, k=3
     * Oxygen-to-hetero: x0=0, xmin=0.75, k=5
     * RDBE: x0=0, xmin=2, k=2
     *
     * @param special if true, then oxygen/hetero backbones are considered in the scoring
     * @return A MolecularFormulaScorer with default parameters
     */
    public static MolecularFormulaScorer createDefaultCompoundScorer(boolean special) {
        return special ? new DefaultScorer() : new ImprovedHetero2CarbonScorer();
    }

    /**
     * @see #createDefaultCompoundScorer(boolean)
     */
    public static MolecularFormulaScorer createDefaultCompoundScorer() {
        return createDefaultCompoundScorer(true);
    }

    public static class DefaultScorer implements MolecularFormulaScorer, Parameterized {
        private ImprovedHetero2CarbonScorer scorer = new ImprovedHetero2CarbonScorer();
        private SpecialMoleculeScorer special = new SpecialMoleculeScorer();

        @Override
        public double score(MolecularFormula formula) {
            return Math.max(scorer.score(formula), special.score(formula));
        }

        public ImprovedHetero2CarbonScorer getScorer() {
            return scorer;
        }

        public SpecialMoleculeScorer getSpecial() {
            return special;
        }

        public void setScorer(ImprovedHetero2CarbonScorer scorer) {
            this.scorer = scorer;
        }

        public void setSpecial(SpecialMoleculeScorer special) {
            this.special = special;
        }

        @Override
        public <G, D, L> void importParameters(ParameterHelper helper, DataDocument<G, D, L> document, D dictionary) {
            scorer = (ImprovedHetero2CarbonScorer) helper.unwrap(document, document.getFromDictionary(dictionary, "standardScorer"));
            special = (SpecialMoleculeScorer) helper.unwrap(document, document.getFromDictionary(dictionary, "specialMoleculeScorer"));
        }

        @Override
        public <G, D, L> void exportParameters(ParameterHelper helper, DataDocument<G, D, L> document, D dictionary) {
            document.addToDictionary(dictionary, "standardScorer", helper.wrap(document, scorer));
            document.addToDictionary(dictionary, "specialMoleculeScorer", helper.wrap(document, special));
        }
    }


}
