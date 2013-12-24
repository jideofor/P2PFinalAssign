P2PFinalAssign
==============
This is the final program.You would have to include the gson jar and json jar to run the program.
I implemented the simplified version of the P2P.
I implemented the pastry prefix routing.
I built my data structure to store words->url->rank.
My routing table stores node handles.
My routing is similar to pastry. I route nodes to the nearest node.
I also built classes to represent different types of messages.
The Joining of nodes works.
The indexing of messages and searching for words works seperately but does not work when the system is brought together. The Problem is my nodes seem to be only able to receive one message before closing. I could not figure out the reason for this behaviour.

