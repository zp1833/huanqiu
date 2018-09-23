dojo.require("jsam.Tree");
/*if (document.getElementById) {
	var tree = new jsam.Tree('Root');
	tree.setBehavior('classic');
	var a = new jsam.TreeItem('1');
	tree.add(a);
	var b = new jsam.TreeItem('1.1');
	a.add(b);
	b.add(new jsam.TreeItem('1.1.1'));
	b.add(new jsam.TreeItem('1.1.2'));
	b.add(new jsam.TreeItem('1.1.3'));
	var f = new jsam.TreeItem('1.1.4');
	b.add(f);
	f.add(new jsam.TreeItem('1.1.4.1'));
	f.add(new jsam.TreeItem('1.1.4.2'));
	f.add(new jsam.TreeItem('1.1.4.3'));
	var c = new jsam.TreeItem('1.2');
	a.add(c);
	c.add(new jsam.TreeItem('1.5.1'));
	c.add(new jsam.TreeItem('1.5.2'));
	c.add(new jsam.TreeItem('1.5.3'));
	a.add(new jsam.TreeItem('1.3'));
	a.add(new jsam.TreeItem('1.4'));
	a.add(new jsam.TreeItem('1.5'));
	var d = new jsam.TreeItem('2');
	tree.add(d);
	var e = new jsam.TreeItem('2.1');
	d.add(e);
	e.add(new jsam.TreeItem('2.1.1'));
	e.add(new jsam.TreeItem('2.1.2'));
	e.add(new jsam.TreeItem('2.1.3'));
	d.add(new jsam.TreeItem('2.2'));
	d.add(new jsam.TreeItem('2.3'));
	d.add(new jsam.TreeItem('2.4'));
	document.write(tree);
}*/
if (document.getElementById) {
	var tree = new jsam.TreeBase({
	  text:'Root', 
	  action: function(txt) {alert(txt.cc);},
	  cc:"xxxxxxx"});
	
	tree.setBehavior('classic');
	var a = new jsam.TreeItem({text:'1'});
	tree.add(a);
	var b = new jsam.TreeItem({text:'1.1'});
	a.add(b);
	b.add(new jsam.TreeItem({text:'1.1.1'}));
	b.add(new jsam.TreeItem({text:'1.1.2'}));
	b.add(new jsam.TreeItem({text:'1.1.3'}));
	var f = new jsam.TreeItem({text:'1.1.4'});
	b.add(f);
	f.add(new jsam.TreeItem({text:'1.1.4.1'}));
	f.add(new jsam.TreeItem({text:'1.1.4.2'}));
	f.add(new jsam.TreeItem({text:'1.1.4.3'}));
	var c = new jsam.TreeItem({text:'1.2'});
	a.add(c);
	c.add(new jsam.TreeItem({text:'1.5.1'}));
	c.add(new jsam.TreeItem({text:'1.5.2'}));
	c.add(new jsam.TreeItem({text:'1.5.3'}));
	a.add(new jsam.TreeItem({text:'1.3'}));
	a.add(new jsam.TreeItem({text:'1.4'}));
	a.add(new jsam.TreeItem({text:'1.5'}));
	var d = new jsam.TreeItem({text:'2'});
	tree.add(d);
	var e = new jsam.TreeItem({text:'2.1'});
	d.add(e);
	e.add(new jsam.TreeItem({text:'2.1.1'}));
	e.add(new jsam.TreeItem({text:'2.1.2'}));
	e.add(new jsam.TreeItem({text:'2.1.3'}));
	d.add(new jsam.TreeItem({text:'2.2'}));
	d.add(new jsam.TreeItem({text:'2.3'}));
	d.add(new jsam.TreeItem({text:'2.4'}));
	
	//document.write(tree);
	var x = dojo.byId('tree1');
	
	x.appendChild(tree.build());
	//x.innerHTML = tree.toString();
	
}
