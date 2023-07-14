package com.farmy.backend.exception;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RecordNotFoundException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException( @Positive @NotNull Long id) {
        super("Could not find record " + id);
    }

    public RecordNotFoundException(int _id) {
		 super("Could not find record " + id);
    }
}
