open-pocket
===========

Open Source backend and CLI for Pocket

Simple Command Line Interface for accessing encrypted data stored in the Android
PIM application 'Pocket' [https://market.android.com/details?id=com.citc.wallet].
Simply run application with path to mounted DropBox folder (i.e. ./Dropbox/SecureWallet/)

Pocket is (c) Tim Clark and under a closed source license with no public API or
specification. This client thus exist only due to carefully inspecting Pockets
behavior, the wallet SQLite database and related research. The greatest 'leak' 
of implementation details by Tim is probably this post on stackoverflow:
[http://stackoverflow.com/questions/4573392/aes-cipher-not-picking-up-iv]

It goes without saying, this application may fail at any moment as Tim changes
file formats or encryption algorithms. It was tested against Pocket 2.3.

FreeBSD license
===============

Copyright (c) 2012, Casper Bang (casper.bang@gmail.com)
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the FreeBSD Project.

