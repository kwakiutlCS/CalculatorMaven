$(function() {
	//detectPressedKeys();
	detectBtnClick();
	//validateEntry();
	
	//console.log(getLastNumber());
});


// deals with keyboard strokes
var detectPressedKeys = function() {
	$(document).keydown(function(e) {
		var key = e.which;
		var mapping = {
				13: "EqualsSimples",
				106: "Mult",
				107: "Add",
				109: "Minus",
				110: "Dot",
				111: "Div",
		};
		
		if (key >= 48 && key <= 57) {
			$("#simpleKeyBoard\\:key"+(key-48)).click();
		}
		else if (key >= 96 && key <= 105) {
			$("#simpleKeyBoard\\:key"+(key-96)).click();
		}
		else if (key in mapping) {
			$("#simpleKeyBoard\\:key"+mapping[key]).click();
		}
	});
}


// overrides button click
var detectBtnClick = function() {
	$(".num").click(function(e) {
		var n = e.currentTarget.value;
		addNumber(n);
		return false;
	});
	
	$(".dotBtn").click(function(e) {
		addDot();
		return false;
	});
	
	$(".binOpBtn").click(function(e) {
		var n = e.currentTarget.value;
		addBinOperator(n);
		return false;
	});
}

var validateEntry = function() {
	var screen = $("#simpleKeyBoard\\:expression").html();
	
}

// adds a digit if possible
var addNumber = function(n) {
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	
	if (getLastNumber() === "0") {
		screen.val(text.substring(0, text.length-1)+n); 
	}
	else {
		screen.val(text+n); 
	}
}

// adds a dot if possible
var addDot = function() {
	var screen = $("#simpleKeyBoard\\:expression");
	var last = getLastNumber();
	
	if (last.indexOf(".") === -1) {
		if (last === "")
			screen.val(screen.val()+"0.");
		else 
			screen.val(screen.val()+".");
	}
}

// adds a binary operator if possible
var addBinOperator = function(n) {
	var ignore = ["("];
	var inputs = ["\u00D7", "-", "+", "\u00F7"];
	var replace = ["*", "-", "+", "/"];
	var output = replace[inputs.indexOf(n)];
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	var lastLetter = text.charAt(text.length-1);
	
	if (replace.indexOf(lastLetter) != -1) {
		screen.val(text.substring(0, text.length-1)+output);
	}
	else if (ignore.indexOf(lastLetter) === -1) {
		screen.val(text+output);
	}
}


// returns last number in the expression (if any)
var getLastNumber = function() {
	var screen = $("#simpleKeyBoard\\:expression").val();
	var lastNumber = "";
	
	var index = screen.length-1;
	var char = screen.charAt(index);
	while ((char >= "0" && char <= "9") || char === ".") {
		lastNumber = char+lastNumber;
		char = screen.charAt(--index);
	}
	
	return lastNumber;
}