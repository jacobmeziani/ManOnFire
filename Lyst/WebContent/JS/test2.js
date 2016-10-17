
function imgLoaded(img){
    var $img = $(img);
 
    $img.addClass('loaded');
};

class LystItem{
	constructor(name, belongingList){
		this.name = name;
		this.belongingList = belongingList;
	}
}

var ListItem = React.createClass({
	  render: function() {
	    return (
		<div id="leftPic"><div id="leftPic">
		<img id="thesauce" src={"https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/" + this.props.imagePath}
		className="img-responsive img-circle center-block vsImage"/>
	</div></div>);
	  }
	});

var RatingsHolder = React.createClass({
	  render: function() {
	    return (
		<div id="leftPic"><div id="leftPic">
		<img id="thesauce" src={"https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/" + this.props.imagePath}
		className="img-responsive img-circle center-block vsImage"/>
	</div></div>);
	  }
	});

var RatingWidgetHolder = React.createClass({
	  render: function() {
		  var theRatingWidgets = [];
		  var leftItem = this.props.leftItem;
		  var rightItem = this.props.rightItem;		  
		  this.props.ratings.forEach(function(rating){
			  theRatingWidgets.push(<RatingWidget rightItem={rightItem} leftItem={leftItem} attributeName={rating} key={rating}/>);
		  });
	    return (
	    		<div className="container-fluid">
	    			{theRatingWidgets}
	    			<div className="col-xs-12 col-sm-6 col-md-4">
    					<div className="attributeTitle centerAlign">
    						Submit!
    					</div>
	    				<button type="button" className="btn btn-circle btn-xl-submit center-block">
	    					<span className="glyphicon glyphicon-ok"></span>
	    				</button>
	    			</div>
	    		</div>
	);
	  }
	});

var RatingWidget = React.createClass({
	  render: function() {
	    return (
	    		<div className="col-xs-12 col-sm-6 col-md-4">
		    		<div className= "centerSauce">
		    			<div className="attributeTitle centerAlign">
		    				{this.props.attributeName}
		    			</div>
						<div className="rating-gauge-padding">
							<div id={"gauge-"+this.props.attributeName} className="gauge-class">
							</div>
						</div>
						<div className="slider-padding">
							<div className="col-xs-6 col-sm-6 col-md-6">
								<img className="img-responsive img-circle leftMiniImage" src={"https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/"+this.props.leftItem.picPath}/>
							</div>
							<div className="col-xs-6 col-sm-6 col-md-6">
								<img className="img-responsive img-circle rightMiniImage" src={"https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/"+this.props.rightItem.picPath}/>
							</div>
							<div className="slider" id="circles-slider" data-leftItemName={this.props.leftItem.name} data-rightItemName={this.props.rightItem.name} data-attributeName={this.props.attributeName}>
							</div>
						</div>
					</div>
				</div>);
	  }
	});

	var ListItems = React.createClass({
	  render: function() {
	    return (
	    		<div className="row">
	    			<div className = "row">
	    				<div id="list" className="centerAlign">
	    					<h3 id="listNameH3">
	    						{this.props.leftItem.belongingList}
	    					</h3>
	    				</div>
	    			</div>
					<div className=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
						<ListItem imagePath={this.props.leftItem.picPath}/>
					</div>
					<div id="centerContent" className="hidden-xs hidden-sm col-md-2 col-lg-2 centerAlign">
						<div className="row">
							<h1>VS</h1>
						</div>
						<button type="button" className="btn btn-circle btn-xl center-block">
							<span className="glyphicon glyphicon-globe"></span>
						</button>
						<h4 className="centerAlign">View Ratings</h4>
					</div>
					<div className="col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
						<ListItem imagePath={this.props.rightItem.picPath}/>
					</div>
					<div className="row" id="bottomRow">
						<div id="leftName" className="hidden-xs hidden-sm col-md-5 col-lg-5 centerAlign">
							<h3 id="leftNameH3">
							{this.props.leftItem.name}
							</h3>
						</div>
						<div id="rightName" className="col-xs-4 col-sm-4 hidden-md hidden-lg centerAlign">
							<h3 id="rightNameH3" className="pull-right">
							{this.props.leftItem.name}
							</h3>
						</div>
						<div id="miniVs" className="col-xs-4 col-sm-4 hidden-md hidden-lg centerAlign">
							<h1>VS</h1>
						</div>
						<div className="hidden-xs hidden-sm col-md-2 col-lg-2">
						</div>
						<div id="rightName" className="hidden-xs hidden-sm col-md-5 col-lg-5 centerAlign">
							<h3 id="rightNameH3">
							{this.props.rightItem.name}
							</h3>
						</div>
						<div id="rightName" className="col-xs-4 col-sm-4 hidden-md hidden-lg centerAlign">
							<h3 id="rightNameH3" className="pull-left">
							{this.props.rightItem.name}
							</h3>
						</div>
					</div>
				</div>
	    );
	  }
	});
	
	var MatchupPage = React.createClass({
		componentDidMount: function() {
			SetupSliders();
		  },
		
		  render: function() {
			  var dataSauce = '{ "attributes": [ "Shooting",  "Dribbling", "Dunking","Rebounding","IQ", "Defense" ], "leftItem": {"name": "Kevin Love","picPath": "NbaPlayers/kevinlove.jpg", "belongingList": "NBA Players"},"rightItem": {"name": "Rudy Gobert","picPath": "NbaPlayers/rudygobert.jpg","belongingList": "NBA Players" }}';
			  var obj = jQuery.parseJSON( dataSauce );
		    return (
		    		<div>
		    		<ListItems leftItem={obj.leftItem} rightItem={obj.rightItem}  />
		    		<RatingWidgetHolder leftItem={obj.leftItem} rightItem={obj.rightItem} ratings={obj.attributes}/>
		    		</div>
		);
		  }
		});

ReactDOM.render(
	    <MatchupPage  />,
	    document.getElementById("leggo")
	  );




