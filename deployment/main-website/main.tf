########################################################################################################################
## Bucket config
########################################################################################################################

resource "aws_s3_bucket" "marcuscraske-dot-com" {
    bucket = "marcuscraske.com"
}

resource "aws_s3_bucket_website_configuration" "marcuscraske-dot-com" {
    bucket = aws_s3_bucket.marcuscraske-dot-com.id

    index_document {
        suffix = "index.html"
    }

    error_document {
        key = "error.html"
    }

}
resource "aws_s3_bucket_acl" "marcuscraske-dot-com" {
    bucket = aws_s3_bucket.marcuscraske-dot-com.id
    acl = "public-read"
}
resource "aws_s3_bucket_policy" "marcuscraske-dot-com" {
    bucket = aws_s3_bucket.marcuscraske-dot-com.id
    policy = jsonencode({
        Version = "2012-10-17"
        Statement = [
            {
                Sid         = "PublicReadGetObject"
                Effect      = "Allow"
                Principal   = "*"
                Action      = "s3:GetObject"
                Resource    = [
                    aws_s3_bucket.marcuscraske-dot-com.arn,
                    "${aws_s3_bucket.marcuscraske-dot-com.arn}/*"
                ]
            }
        ]
    })
}

########################################################################################################################
## Copying files
########################################################################################################################

module "template_files" {
    source = "hashicorp/dir/template"
    base_dir = "${path.module}/../../website/_site"
    template_vars = {
    }
}

resource "aws_s3_object" "marcuscraske-dot-com" {
    for_each        = module.template_files.files
    bucket          = aws_s3_bucket.marcuscraske-dot-com.id
    key             = each.key
    source          = each.value.source_path
    content         = each.value.content
    content_type    = each.value.content_type
    etag            = each.value.digests.md5
}
