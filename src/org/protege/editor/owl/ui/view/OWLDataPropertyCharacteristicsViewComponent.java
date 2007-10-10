package org.protege.editor.owl.ui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChange;
import org.semanticweb.owl.model.OWLOntologyChangeListener;
import org.semanticweb.owl.model.RemoveAxiom;
import org.semanticweb.owl.util.FilteringOWLOntologyChangeListener;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21-Feb-2007<br><br>
 */
public class OWLDataPropertyCharacteristicsViewComponent extends AbstractOWLDataPropertyViewComponent {

    private static final Logger logger = Logger.getLogger(OWLDataPropertyCharacteristicsViewComponent.class);


    private JCheckBox checkBox;

    private OWLDataProperty prop;

    private OWLOntologyChangeListener listener;


    protected OWLDataProperty updateView(OWLDataProperty property) {
        prop = property;
        checkBox.setSelected(false);
        for (OWLOntology ont : getOWLModelManager().getActiveOntologies()) {
            if (ont.getFunctionalDataPropertyAxiom(prop) != null) {
                checkBox.setSelected(true);
                break;
            }
        }
        return property;
    }


    public void disposeView() {
        super.disposeView();
        getOWLModelManager().removeOntologyChangeListener(listener);
    }


    public void initialiseView() throws Exception {
        setLayout(new BorderLayout());
        checkBox = new JCheckBox("Functional");
        add(checkBox, BorderLayout.NORTH);
        listener = new FilteringOWLOntologyChangeListener() {
            public void visit(OWLFunctionalDataPropertyAxiom axiom) {
                updateView(prop);
            }
        };
        getOWLModelManager().addOntologyChangeListener(listener);
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateOntology();
            }
        });
    }


    private void updateOntology() {
        if (prop == null) {
            return;
        }
        OWLDataFactory df = getOWLModelManager().getOWLDataFactory();
        OWLAxiom ax = df.getOWLFunctionalDataPropertyAxiom(prop);
        if (checkBox.isSelected()) {
            OWLOntology ont = getOWLModelManager().getActiveOntology();
            getOWLModelManager().applyChange(new AddAxiom(ont, ax));
        }
        else {
            List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
            for (OWLOntology ont : getOWLModelManager().getActiveOntologies()) {
                changes.add(new RemoveAxiom(ont, ax));
            }
            getOWLModelManager().applyChanges(changes);
        }
    }
}
