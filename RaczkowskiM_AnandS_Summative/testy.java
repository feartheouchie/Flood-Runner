y = caluclations
x2;
manX = 163
ymin = 0;


isPlatform = false;
for (int i = 0; i < platformsX.size(); i++){
	x2 = platformsX.get(i);
	t = waitTimes[platformsT.get(i)];
	if (manX >= x2 && manX <= x2 + t){
		ymin = platformsY.get(i)
		isPlatform = true;
		break;
	}
}
if (!isPlatform){
	ymin = 0;
}
	

if (y > ymin)
	isjump = true;
else{
	if (ylast > ymin)
		isjump = false;
	else
		isjump = true;
}

if (y > 600)
	screen = "fail";


ylast = y