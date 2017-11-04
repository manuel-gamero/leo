<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="modal fade" id="imagemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<img src="" class="imagepreview modal-content-image" style="width: 100%;" >
			</div>
			<s:if test="#title != ''">
				<p id='title' class="text-center text-uppercase"><strong>${param.title}</strong></p>
			</s:if>
			<s:if test="#subtitle != ''">
				<a id="link" href="#">
					<p id='subtitle' class="text-center text-uppercase">${param.subtitle}</p>
				</a>
			</s:if>
		</div>
	</div>
</div>
<script type="text/javascript">

function centerModal() {
	$('#imagemodal').css('display', 'block');
    var $dialog = $('#imagemodal').find(".modal-dialog");
    var offset = ($(window).height() - $dialog.height()) / 2;
    // Center modal vertically in window
    $dialog.css("margin-top", offset);
}

$('.modalImage').on('click', function(e) {
	e.preventDefault();
	$('.title-link').remove();
	$('.imagepreview').attr('src', $(this).attr('href'));
	var title = $(this).attr('title');
	var link = $(this).attr('data-link');
	if( title != null ){
		if ( $('#subtitle').length ){
			if(link != ''){
				$('#subtitle').text(title);
				$('#link').attr("href", link);
			}
			else{
				$('.modal-content').find('#link').replaceWith( "<p class='text-center text-uppercase title-link'>" + title + "</p>" );
				$('#link').attr("href", link);
			}
		}
		else{
			if(link != ''){
				$('.modal-content').append( '<a id="link" href="' + link + '"><p id="subtitle" class="text-center text-uppercase">' + title + '</p></a>' );
			}
			else{
				$('.modal-content').append( "<p class='text-center text-uppercase title-link'>" + title + "</p>" );
			}
		}
	}
	$('#imagemodal').modal('show');
	centerModal();
});

</script>
