package org.apache.jmeter.assertions.gui;

import org.apache.jmeter.assertions.JSONAssertion;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.layout.VerticalLayout;

public class JSONAssertionGui extends AbstractAssertionGui {

	/**
	 * 
	 */
	private static final long serialVersionUID = 240L;

	public JSONAssertionGui() {
		init();
	}
	
	@Override
	public String getLabelResource() {
		
		return "json_assertion_title";
	}

	@Override
	public TestElement createTestElement() {
		JSONAssertion el = new JSONAssertion();
		modifyTestElement(el);
		return el;
	}

	@Override
	public void modifyTestElement(TestElement element) {
		configureTestElement(element);
		
	}
	
	public void init(){
		setLayout(new VerticalLayout(5,VerticalLayout.BOTH,VerticalLayout.TOP));
		setBorder(makeBorder());
		add(makeTitlePanel());
	}

}
