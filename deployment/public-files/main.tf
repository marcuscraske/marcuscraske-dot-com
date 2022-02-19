resource "aws_s3_bucket" "files-marcuscraske-dot-com" {
    bucket = "files.marcuscraske.com"
}

resource "aws_s3_bucket_website_configuration" "files-marcuscraske-dot-com" {
    bucket = aws_s3_bucket.files-marcuscraske-dot-com.id

    index_document {
        suffix = "index.html"
    }

    error_document {
        key = "error.html"
    }

}
resource "aws_s3_bucket_acl" "files-marcuscraske-dot-com" {
    bucket = aws_s3_bucket.files-marcuscraske-dot-com.id
    acl = "public-read"
}
resource "aws_s3_bucket_policy" "files-marcuscraske-dot-com" {
    bucket = aws_s3_bucket.files-marcuscraske-dot-com.id
    policy = jsonencode({
        Version = "2012-10-17"
        Statement = [
            {
                Sid         = "PublicReadGetObject"
                Effect      = "Allow"
                Principal   = "*"
                Action      = "s3:GetObject"
                Resource    = [
                    aws_s3_bucket.files-marcuscraske-dot-com.arn,
                    "${aws_s3_bucket.files-marcuscraske-dot-com.arn}/*"
                ]
            }
        ]
    })
}
resource "aws_s3_bucket_cors_configuration" "example" {
    bucket = aws_s3_bucket.files-marcuscraske-dot-com.id

    cors_rule {
        allowed_headers = ["*"]
        allowed_methods = ["GET"]
        allowed_origins = ["*"]
        expose_headers  = ["ETag"]
        max_age_seconds = 3000
    }
}
