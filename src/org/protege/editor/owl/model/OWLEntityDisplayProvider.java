package org.protege.editor.owl.model;

import javax.swing.JComponent;

import org.semanticweb.owl.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 25-May-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public interface OWLEntityDisplayProvider {

    public boolean canDisplay(OWLEntity owlEntity);


    public JComponent getDisplayComponent();
}
