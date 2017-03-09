# Lisp-Language-interpreter-in-Java

Lisp Interpreter Project part1
Author: Rashmi J Rao
-------------------------------

How to compile: 

	# Place all the files Parser.java, SExp.java. Tokenizer.java in the same director
	# cd into that directory
	# Type this command to compile:
	make


How to run:

	# After compiling, in the directory containing all the files, type this command to run:
	java Tokenizer
	# Type the s-exp
	# Hit <enter> key
	# Type $
	# Hit <enter> key to get the dotted expression
	# Type $$ followed by <enter> key to stop

	Eg:
	% java Tokenizer
	% (A B)
	% $
	% >> (A.(B.NIL))
	% ()
	% $$
	% >> NIL
	% Bye!

Additional note:

	# Do not type $$ in the next line after $ to end. The program just throws an array out of bound exception
	# Do not type $ in the same line after the expression, it's usually ignored but might throw an error in some cases.
	# Just typing $$ after getting the output of an expression will end the program but will produce an error.
