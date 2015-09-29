/**
* Copyright (c) Microsoft Corporation
* 
* All rights reserved. 
* 
* MIT License
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files 
* (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, 
* publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
* subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
* ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH 
* THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.microsoft.azure.shortcuts;

import java.util.ArrayList;

import com.microsoft.azure.shortcuts.implementation.SupportsListing;
import com.microsoft.windowsazure.management.models.RoleSizeListResponse.RoleSize;

// Encapsulates the API related to VM sizes
class Sizes implements 
	SupportsListing {
	
	final Azure azure;
	Sizes(Azure azure) {
		this.azure = azure;
	}
	
	// Return the list of available size names supporting the specified type of compute service
	public String[] list(boolean supportingVM, boolean supportingCloudServices) {
		try {
			ArrayList<RoleSize> sizes = azure.management.getRoleSizesOperations().list().getRoleSizes();
			String[] names = new String[sizes.size()];
			int i=0;
			for(RoleSize size : sizes) {
				if(supportingVM && size.isSupportedByVirtualMachines() 
				|| supportingCloudServices && size.isSupportedByWebWorkerRoles()) {
					names[i++] = size.getName();
				}
			}
			return names;
		} catch (Exception e) {
			// Not very actionable, so just return an empty array
			return new String[0];
		}			
	}

	// Returns all available size names
	public String[] list() {
		return list(true, true);
	}
}