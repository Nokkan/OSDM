package Gtm.actions;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import Gtm.GTMTool;


public abstract class BasicGtmAction extends BaseSelectionListenerAction {
	
		
		protected IEditingDomainProvider editingDomainProvider = null;
		
	
		public BasicGtmAction(String text, IEditingDomainProvider editingDomainProvider) {
			super(text);
			this.editingDomainProvider = editingDomainProvider;
		}

		public void run() {
			run( this.getStructuredSelection() );
			return;
		}
		
		protected void run(IStructuredSelection structuredSelection) {

			GTMTool tool = GtmUtils.getGtmTool();
			
			EditingDomain domain = GtmUtils.getActiveDomain();
			
			if (tool == null) {
				MessageBox dialog =  new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR | SWT.OK);
				dialog.setText("no data found");
				dialog.open(); 
				return;
			}
			
			CompoundCommand preparationCommand = GtmUtils.getPreparationCommand(tool, domain);
			
			if (preparationCommand != null && !preparationCommand.isEmpty()) {
				domain.getCommandStack().execute(preparationCommand);
			}
			
			runAction(tool);
		
		}		


		protected abstract void runAction(GTMTool tool);

		protected boolean checkSelection(IStructuredSelection selection)
		{
			if (GtmUtils.getGtmTool() == null) return false;
			
			return true;
		}
		
 	
		
	    public boolean updateSelection (IStructuredSelection selection)
	    {
	  		this.setEnabled(false);

	  		if (checkSelection(selection)) 	{
	  			this.setEnabled(true);
	  			return true;
	  		}
	  		return false;
	    }	
	    
	

}
