
	/////////////////////////////////////////////////////////////
	//
	//code[1].sc / data[1].007
	//   By this Scilab code, you can convert an SPM data file
	//   of Bruker Corporation's into ISO data format.
	// (c) 2015 National Institute for Materials Science (NIMS)
	//
	/////////////////////////////////////////////////////////////

	clear();

	[fname, dpath, findex] = uigetfile({'*.007',  ' (*.007)'}	);


	if findex==0 

	       disp('User selected Cancel');
	       messagebox('User selected Cancel');

	else 


		PATH = fullfile(dpath, fname);


		fd1 = mopen(PATH,'rb');

		line=mgetl(PATH ,100);



		L1=grep(line,'\Samps/line');
		PixelsX = msscanf(line(L1),'\Samps/line: %s');

		im_xsiz = msscanf(PixelsX,'%g');


		L2=grep(line,'\Lines');
		PixelsY = msscanf(line(L2),'\Lines: %s');

		im_ysiz = msscanf(PixelsY,'%g');


		L3=grep(line,'\X sensitivity');
		xsens = msscanf(line(L3),'\X sensitivity: %g');



		L4=grep(line,'\Y sensitivity');

		ysens = msscanf(line(L4),'\Y sensitivity: %g');


		L5=grep(line,'\Capture direction');


		mseek(20480,fd1,'set');


		a=mget(im_xsiz*im_ysiz,'sl',fd1);

		mclose(fd1);


		for i=1:im_xsiz
			for j=1:im_ysiz

				z(im_xsiz+1-i,j)=a((i-1)*im_ysiz+j);

 	 		end
  		end


		zmax=max(z(:));
		zmin=min(z(:));
		Z=255*(z-zmin)/(zmax-zmin);
		clf();
		Matplot(Z);
		set(gcf(),'color_map',graycolormap(256));

		colorbar(zmin, zmax);




		xlength=xsens*im_xsiz

		ylength=ysens*im_ysiz




		line(1)="ISO/TC 201 SPM data transfer format"
		line(2)="general information"
		line(3)=""
		line(4)=""
		line(5)=""
		line(6)=""
		line(7)=""
		line(8)="MAP_SC"
		line(9)="-1"
		line(10)="-1"
		line(11)="-1"
		line(12)="-1"
		line(13)="-1"
		line(14)="-1"
		line(15)="-1"
		line(16)="scan information"
		line(17)="REGULAR MAPPING"
		line(18)=""
		line(19)=""
		line(20)="X"
		line(21)="left to right"
		line(22)="Y"
		line(23)="top to bottom"
		line(24)=sprintf("%d",im_xsiz)
		line(25)=sprintf("%d",im_ysiz)
		line(26)="um"
		line(27)="um"
		line(28)=sprintf("%f",xsens*im_xsiz)
		line(29)=sprintf("%f",ysens*im_ysiz)
		line(30)=""
		line(31)=""
		line(32)=""
		line(33)=""
		line(34)=""
		line(35)=""
		line(36)=""
		line(37)=""
		line(38)=""
		line(39)=""
		line(40)=""
		line(41)=""
		line(42)=""
		line(43)=""
		line(44)=""
		line(45)=""
		line(46)=""
		line(47)=""
		line(48)="environment description"
		line(49)=""
		line(50)=""
		line(51)=""
		line(52)=""
		line(53)=""
		line(54)="probe description"
		line(55)=""
		line(56)=""
		line(57)=""
		line(58)=""
		line(59)=""
		line(60)=""
		line(61)=""
		line(62)=""
		line(63)=""
		line(64)="sample description"
		line(65)=""
		line(66)=""
		line(67)=""
		line(68)="single-channel mapping description"
		line(69)=""
		line(70)="nm"
		line(71)=""
		line(72)="spectroscopy description"
		line(73)=""
		line(74)=""
		line(75)=""
		line(76)=""
		line(77)=""
		line(78)=""
		line(79)=""
		line(80)=""
		line(81)=""
		line(82)=""
		line(83)=""
		line(84)=""
		line(85)=""
		line(86)=""
		line(87)="data treatment description"
		line(88)=""
		line(89)=""
		line(90)=""
		line(91)=""
		line(92)=""
		line(93)="multi-channel mapping description"
		line(94)="1"
		line(95)=""
		line(96)=""
		line(97)=""
		line(98)=""
		line(99)=""
		line(100)=""
		line(101)=""
		line(102)=""
		line(103)=""
		line(104)=""
		line(105)=""
		line(106)=""
		line(107)=""
		line(108)=""
		line(109)=""
		line(110)=""
		line(111)=""
		line(112)=""
		line(113)=""
		line(114)=""
		line(115)=""
		line(116)=""
		line(117)=""
		line(118)=""
		line(119)=""
		line(120)=""
		line(121)=""
		line(122)=""
		line(123)=""
		line(124)=""
		line(125)=""
		line(126)=""
		line(127)=""
		line(128)="end of header"


		[fname, pname, findex] = uiputfile('','Save as','default')

		path =sprintf("%s\\\\%s.spm",pname, fname)

		cmd =sprintf("del %s",path)

		unix(cmd);

		fd=mopen(path,'wt');
		mseek(0,fd,'end');


		for i=1:128
		mfprintf(fd,"%s\n",line(i));
		end

		mseek(0,fd,'end');


		for i=1:im_xsiz
			for j=1:im_ysiz
				mfprintf(fd,"%f\n",z(i,j));
			end
		end


		mfprintf(fd,"end of experiment\n");

		mclose(fd);

	end;

