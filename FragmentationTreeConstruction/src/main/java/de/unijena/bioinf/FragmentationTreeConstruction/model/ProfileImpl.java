package de.unijena.bioinf.FragmentationTreeConstruction.model;

import de.unijena.bioinf.ChemistryBase.chem.ChemicalAlphabet;
import de.unijena.bioinf.ChemistryBase.chem.FormulaConstraints;
import de.unijena.bioinf.ChemistryBase.ms.Deviation;
import de.unijena.bioinf.ChemistryBase.ms.MeasurementProfile;

public class ProfileImpl implements MeasurementProfile {

    private Deviation expectedIonMassDeviation, expectedMassDifferenceDeviation, expectedFragmentMassDeviation;
    private FormulaConstraints constraints;

    public ProfileImpl() {

    }

    public ProfileImpl(MeasurementProfile profile) {
        this.expectedIonMassDeviation = profile.getExpectedIonMassDeviation();
        this.expectedMassDifferenceDeviation = profile.getExpectedMassDifferenceDeviation();
        this.expectedFragmentMassDeviation = profile.getExpectedFragmentMassDeviation();
        this.constraints = new FormulaConstraints(new ChemicalAlphabet());
    }

    public Deviation getExpectedIonMassDeviation() {
        return expectedIonMassDeviation;
    }

    public void setExpectedIonMassDeviation(Deviation expectedIonMassDeviation) {
        this.expectedIonMassDeviation = expectedIonMassDeviation;
    }

    public Deviation getExpectedMassDifferenceDeviation() {
        return expectedMassDifferenceDeviation;
    }

    public void setExpectedMassDifferenceDeviation(Deviation expectedMassDifferenceDeviation) {
        this.expectedMassDifferenceDeviation = expectedMassDifferenceDeviation;
    }

    public Deviation getExpectedFragmentMassDeviation() {
        return expectedFragmentMassDeviation;
    }

    public void setFormulaConstraints(FormulaConstraints constraints) {
        this.constraints = constraints;
    }

    @Override
    public FormulaConstraints getFormulaConstraints() {
        return constraints;
    }

    public void setExpectedFragmentMassDeviation(Deviation expectedFragmentMassDeviation) {
        this.expectedFragmentMassDeviation = expectedFragmentMassDeviation;
    }
}
